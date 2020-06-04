package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import bg.tu.varna.si.model.CarCategory;
import bg.tu.varna.si.model.CarType;
import bg.tu_varna.si.rentacarapp.R;

public class CarNew extends AppCompatActivity {
    EditText regNumber;
    EditText brand;
    EditText kilometrage;
    Spinner typeSpinner;
    Spinner categorySpinner;
    RadioGroup smokingRadioGroup;
    RadioButton radioButton;
    CarType[] typesArray = CarType.values().clone();
    CarCategory[] categoriesArray = CarCategory.values().clone();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_new);
        regNumber = findViewById(R.id.car_registration_number);
        brand = findViewById(R.id.car_brand);
        kilometrage = findViewById(R.id.car_kilometrage);
        typeSpinner = findViewById(R.id.car_type);
        categorySpinner = findViewById(R.id.car_category);
        smokingRadioGroup = findViewById(R.id.smokers_non);
        typeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Log.d("Car types count: ", String.valueOf(typesArray.length));
        ArrayAdapter typeAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, typesArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        categorySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        Log.d("Car categories count: ", String.valueOf(categoriesArray.length));
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoriesArray);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);


       /*
        int radioId = smokingRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
       * */

    }

    public void checkButton(View v) {
        int radioId = smokingRadioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();
    }
}
