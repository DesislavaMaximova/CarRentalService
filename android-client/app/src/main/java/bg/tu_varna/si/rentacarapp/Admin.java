package bg.tu_varna.si.rentacarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import static bg.tu_varna.si.rentacarapp.R.layout.administrator;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    private CardView createCompany;
    private CardView createEmployee;
    private CardView editCompany;
    private CardView editEmployee;
    private CardView deleteCompany;
    private CardView deleteEmployee;
    private CardView getAllCompanies;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(administrator);
      createCompany = findViewById(R.id.create_company);
      createEmployee = findViewById(R.id.create_employee);
      editCompany = findViewById(R.id.company_update);
      editEmployee = findViewById(R.id.employee_update);
     // deleteCompany = findViewById(R.id.company_delete);
     // deleteEmployee = findViewById(R.id.employee_delete);
    //  getAllCompanies=findViewById(R.id.getAll_companies);

      createCompany.setOnClickListener(this);
      createEmployee.setOnClickListener(this);
      editCompany.setOnClickListener(this);
      editEmployee.setOnClickListener(this);
      deleteCompany.setOnClickListener(this);
      deleteEmployee.setOnClickListener(this);
      getAllCompanies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(Admin.this, RegisterUser.class);

    }


}
