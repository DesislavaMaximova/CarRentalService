package bg.tu_varna.si.rentacarapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarCategory;
import bg.tu.varna.si.model.CarType;
import bg.tu_varna.si.rentacarapp.Cars;
import bg.tu_varna.si.rentacarapp.CompanyEmployees;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarNew extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    public static final String EXTRA_CAR_ID = "carId";

    EditText regNumber;
    EditText brand;
    EditText kilometrage;
    EditText priceForDay;
    Spinner typeSpinner;
    Spinner categorySpinner;
    CheckBox smoking;
    CheckBox available;
    Button chooseImage;
    ImageView carImage;
    Uri carUri;
    Bitmap bitmap;
    FloatingActionButton fabCheck;
    FloatingActionButton back;
    ApiService apiService;
    CarCategory carCategory;
    CarType carType;
    long carId;
    ArrayAdapter<CarType> typeAdapter;
    ArrayAdapter<CarCategory> categoryAdapter;
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_new);
        carImage = findViewById(R.id.car_image);
        regNumber = findViewById(R.id.car_registration_number);
        brand = findViewById(R.id.car_brand);
        kilometrage = findViewById(R.id.car_kilometrage);
        priceForDay = findViewById(R.id.car_price_for_day);
        typeSpinner = findViewById(R.id.car_type);
        categorySpinner = findViewById(R.id.car_category);
        smoking = findViewById(R.id.check_smokers);
        available = findViewById(R.id.check_available);
        chooseImage = findViewById(R.id.button_choose_image);
        fabCheck = findViewById(R.id.fab_car_check);
        back = findViewById(R.id.fab_car_back);

        Intent intent = getIntent();
        carId = intent.getLongExtra(EXTRA_CAR_ID, -1L);
        Log.d("CarId: ", String.valueOf(carId));
        apiService = RetrofitService.cteateService(ApiService.class);

        typeAdapter = new ArrayAdapter<CarType>(this,
                android.R.layout.simple_list_item_1, CarType.values());
        typeSpinner.setAdapter(typeAdapter);
        categoryAdapter = new ArrayAdapter<CarCategory>(this,
                android.R.layout.simple_list_item_1, CarCategory.values());
        categorySpinner.setAdapter(categoryAdapter);

        typeSpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(this);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
        if (carId != -1) {
            setTitle("Update car ");
            getCar();
            fabCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    putCar(CompanyId.getCompanyId(), carId, makeCar());
                    handler.postDelayed(updateTimeTask, 1500);
                }
            });
        } else {
            setTitle("New car");
            fabCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postCar(CompanyId.getCompanyId(), makeCar());
                    handler.postDelayed(updateTimeTask, 1500);
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private void putCar(long companyId, long carId, Car car) {
        Call<Car> call = apiService.updateCar(JwtHandler.getJwt(),companyId, carId, car);
        call.enqueue((new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Toast.makeText(CarNew.this,
                        "Car with registration number " +
                                response.body().getRegNumber() + " is updated",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Toast.makeText(CarNew.this,
                        "Error: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void getCar() {
        Call<Car> call = apiService.getCar(JwtHandler.getJwt(), CompanyId.getCompanyId(), carId);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Log.d("Response:", String.valueOf(response.body()));
                regNumber.setText(response.body().getRegNumber());
                brand.setText(response.body().getBrand());
                kilometrage.setText(String.valueOf(response.body().getKilometrage()));
                priceForDay.setText(String.valueOf(response.body().getPriceForDay()));

                CarType carType = response.body().getType();
                if (carType != null) {
                    int spinnerPosition = typeAdapter.getPosition(carType);
                    typeSpinner.setSelection(spinnerPosition);
                }
                CarCategory carCategory = response.body().getCategory();
                if (carCategory != null) {
                    int spinnerPosition = categoryAdapter.getPosition(carCategory);
                    categorySpinner.setSelection(spinnerPosition);
                }
                smoking.setChecked(response.body().getForSmokers());
                available.setChecked(response.body().isAvailable());


                String image = response.body().getImage();
                byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                carImage.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {

            }
        });
    }
    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(CarNew.this, Cars.class);
            startActivity(intent);
        }
    };

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            carUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),carUri);
                carImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void postCar(long companyId, Car car) {

        Call<Car> call = apiService.createCar(JwtHandler.getJwt(), companyId, car);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                Toast.makeText(CarNew.this, "New car was created in database!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Toast.makeText(CarNew.this, "The car can't be saved in database!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String convertToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap = ((BitmapDrawable) carImage.getDrawable()).getBitmap();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private Car makeCar() {
        Car car = new Car();
        String image = convertToString();
        car.setRegNumber(regNumber.getText().toString());
        if (available.isChecked()) {
            car.setAvailable(true);
        } else {
            car.setAvailable(false);
        }
        if (smoking.isChecked()) {
            car.setForSmokers(true);
        } else {
            car.setForSmokers(false);
        }
        car.setCategory(carCategory);
        car.setType(carType);
        car.setBrand(brand.getText().toString());
        long companyId = CompanyId.getCompanyId();
        car.setCompanyId(companyId);
        car.setImage(image);
        car.setKilometrage(Double.parseDouble(kilometrage.getText().toString()));
        car.setPriceForDay(Double.parseDouble(priceForDay.getText().toString()));
        return car;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.car_category) {
            String categoryText = categorySpinner.getSelectedItem().toString();
            carCategory = CarCategory.valueOf(categoryText);
            Log.d("Car category", categoryText);
        } else if (parent.getId() == R.id.car_type) {
            String typeText = typeSpinner.getSelectedItem().toString();
            Log.d("Car type: ", typeText);
            carType = CarType.valueOf(typeText);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
