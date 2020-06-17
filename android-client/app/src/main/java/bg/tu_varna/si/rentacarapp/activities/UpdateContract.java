package bg.tu_varna.si.rentacarapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarStatus;
import bg.tu.varna.si.model.Client;
import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.Status;
import bg.tu_varna.si.rentacarapp.Contracts;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UpdateContract extends AppCompatActivity {
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private final Calendar calendar = Calendar.getInstance();
    ApiService apiService;
    TextView operatorTextView;
    EditText startEditText;
    EditText endEditText;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    EditText clientEditText;
    EditText carEditText;
    Client selectedClient;
    Car selectedCar;
    EditText endReportEditText;
    EditText startReportEditText;
    EditText priceEditText;
    double carPrice;
    CheckBox outsideDamage;
    CheckBox insideDamage;
    CheckBox engineDamage;
    CheckBox transmissionDamage;
    long contractId;
    private Handler handler = new Handler();
    Contract contract = new Contract();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract);

        apiService = RetrofitService.cteateService(ApiService.class);

        FloatingActionButton fabFinish = findViewById(R.id.fab_contract_finish_update);
        operatorTextView = findViewById(R.id.contract_operator_update);
        carEditText = findViewById(R.id.contract_car_update);
        clientEditText = findViewById(R.id.contract_client_update);
        startEditText = findViewById(R.id.start_date_update);
        startReportEditText = findViewById(R.id.start_report_update);
        endEditText = findViewById(R.id.end_date_update);
        endReportEditText = findViewById(R.id.end_status_update);
        priceEditText = findViewById(R.id.final_price_update);
        insideDamage = findViewById(R.id.check_contract_insideDamage);
        outsideDamage = findViewById(R.id.check_contract_outsideDamage);
        engineDamage = findViewById(R.id.check_contract_engineDamage);
        transmissionDamage = findViewById(R.id.check_contract_TransmissionDamage);
        Intent intent = getIntent();
        contractId = intent.getLongExtra("contractId", -1);

        getContract(CompanyId.getCompanyId(), contractId);

        FloatingActionButton fabBack = findViewById(R.id.fab_contract_back_update);


        fabFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contract.setActive(false);
                try {
                    updateContract(CompanyId.getCompanyId(), contractId);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                updateCar();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateContract.this);
                View layout = null;
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = inflater.inflate(R.layout.ratingbar, null);
                final RatingBar ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
                builder.setTitle("Update client's rating");
                builder.setCancelable(true);
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Float value = ratingBar.getRating();
                        selectedClient.setRating(value);
                        updateClient();

                    }
                });
                builder.setView(layout);
                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        startEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(UpdateContract.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                calendar.set(year, month, day);
                                String date = new SimpleDateFormat(DATE_PATTERN).format(calendar.getTime());
                                startEditText.setText(date);
                            }
                        }, year, month, dayOfMonth);
                dpd.show();
            }
        });


        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(UpdateContract.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                calendar.set(year, month, day);
                                String date = new SimpleDateFormat(DATE_PATTERN).format(calendar.getTime());
                                endEditText.setText(date);


                            }
                        }, year, month, dayOfMonth);
                dpd.show();
            }
        });

        insideDamage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice();
            }
        });
        outsideDamage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice();
            }
        });
        transmissionDamage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice();
            }
        });
        engineDamage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculatePrice();
            }
        });
        /////////////
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePrice();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        startEditText.addTextChangedListener(textWatcher);
        endEditText.addTextChangedListener(textWatcher);
    }


    private void getContract(long companyId, long contractId) {
        Call<Contract> call = apiService.getContract(JwtHandler.getJwt(), companyId, contractId);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                Log.d("Contract Response", response.message());
                contract.setPrice(response.body().getPrice());
                contract.setStatusOnEnd(response.body().getStatusOnEnd());
                contract.setEnd(response.body().getEnd());
                contract.setStatusOnStart(response.body().getStatusOnStart());
                contract.setStart(response.body().getStart());
                contract.setId(response.body().getId());
                contract.setClient(response.body().getClient());
                contract.setCar(response.body().getCar());
                contract.setOperator(response.body().getOperator());
                selectedCar = response.body().getCar();
                selectedClient = response.body().getClient();

                operatorTextView.setText(response.body().getOperator().getFirstName() + " " +
                        response.body().getOperator().getLastName());
                carEditText.setText(response.body().getCar().getRegNumber());
                clientEditText.setText(response.body().getClient().getFirstName() + " " +
                        response.body().getClient().getLastName());
                priceEditText.setText(String.valueOf(response.body().getPrice()));
                startReportEditText.setText(response.body().getStatusOnStart().getDescription());
                endReportEditText.setText(response.body().getStatusOnEnd().getDescription());
                String startDate = new SimpleDateFormat(DATE_PATTERN).format(response.body().getStart());
                startEditText.setText(startDate);
                String endDate = new SimpleDateFormat(DATE_PATTERN).format(response.body().getEnd());
                endEditText.setText(endDate);

            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Log.d("Get contract err: ", t.getMessage());
            }
        });
    }

    private void updateContract(long companyId, long contractId) throws ParseException {
        Contract updatedContract = new Contract();
        updatedContract.setOperator(contract.getOperator());
        updatedContract.setCar(contract.getCar());
        updatedContract.setClient(contract.getClient());
        updatedContract.setId(contract.getId());
        updatedContract.setActive(contract.isActive());
        updatedContract.setStart(contract.getStart());
        updatedContract.setStatusOnStart(contract.getStatusOnStart());
        updatedContract.setCompanyId(CompanyId.getCompanyId());
        CarStatus carStatus = new CarStatus();
        if (engineDamage.isChecked() || outsideDamage.isChecked() || insideDamage.isChecked() || transmissionDamage.isChecked()) {
            carStatus.setStatus(Status.DAMAGED);
        } else {
            carStatus.setStatus(Status.OK);
        }
        carStatus.setDescription(endEditText.getText().toString());
        updatedContract.setStatusOnEnd(carStatus);

        Date endDate = new SimpleDateFormat(DATE_PATTERN).parse(endEditText.getText().toString());
        updatedContract.setEnd(endDate);

        updatedContract.setPrice(Double.parseDouble(priceEditText.getText().toString()));

        Call<Contract> call = apiService.updateContract(JwtHandler.getJwt(), companyId, contractId, updatedContract);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateContract.this, "Contract updated!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Log.d("Update error: ", t.getMessage());
            }
        });
        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(UpdateContract.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                calendar.set(year, month, day);
                                String date = new SimpleDateFormat(DATE_PATTERN).format(calendar.getTime());
                                endEditText.setText(date);


                            }
                        }, year, month, dayOfMonth);
                dpd.show();
            }
        });
    }

    public void calculatePrice() {

        if (selectedCar == null) {
            priceEditText.setText("0");
            return;
        }

        carPrice = selectedCar.getPriceForDay();
        double initialPrice = 0;
        Log.d("Base car price: ", String.valueOf(carPrice));

        LocalDate startDate = getDate(startEditText.getText().toString());
        if (startDate == null) {
            priceEditText.setText("0");
            return;
        }

        LocalDate endDate = getDate(endEditText.getText().toString());
        if (endDate == null) {
            priceEditText.setText(String.valueOf(carPrice));
            initialPrice = carPrice;
            priceEditText.setText(String.valueOf(initialPrice));
            return;
        }

        long duration = ChronoUnit.DAYS.between(startDate, endDate);

        initialPrice = duration * carPrice;

        double insideDamagePrice = 0;
        double outsideDamagePrice = 0;
        double engineDamagePrice = 0;
        double transmissionDamagePrice = 0;

        if (insideDamage.isChecked()) {
            insideDamagePrice = initialPrice * 20;
        }
        if (outsideDamage.isChecked()) {
            outsideDamagePrice = initialPrice * 30;

        }
        if (transmissionDamage.isChecked()) {
            transmissionDamagePrice = initialPrice * 50;
        }

        if (engineDamage.isChecked()) {
            engineDamagePrice = initialPrice * 100;
        }

        double contractPrice = initialPrice + insideDamagePrice + outsideDamagePrice + engineDamagePrice + transmissionDamagePrice;
        priceEditText.setText(String.valueOf(contractPrice));

    }

    private LocalDate getDate(String dateToParse) {
        try {
            return LocalDate.parse(dateToParse, dtf);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(UpdateContract.this, Contracts.class);
            startActivity(intent);
        }
    };

    private void updateCar() {
        selectedCar.setAvailable(true);
        Call<Car> call = apiService.updateCar(JwtHandler.getJwt(), CompanyId.getCompanyId(), selectedCar.getCarId(), selectedCar);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Log.d("Updated car:", response.body().getRegNumber());
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.d("Error updating car:", t.getMessage());
            }
        });
    }

    private void updateClient() {
        Call <Client> call = apiService.updateClient(JwtHandler.getJwt(), CompanyId.getCompanyId(), selectedClient.getId(), selectedClient);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()) {
                    handler.postDelayed(updateTimeTask, 1500);
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            intent = new Intent(this, MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

}