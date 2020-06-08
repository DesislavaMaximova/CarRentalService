package bg.tu_varna.si.rentacarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.User;
import bg.tu_varna.si.rentacarapp.activities.EmployeeProfile;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.adapter.EmployeeAdapter;
import bg.tu_varna.si.rentacarapp.viewModels.EmployeeViewModel;

import static bg.tu_varna.si.rentacarapp.R.layout.activity_company_profile;

public class CompanyEmployees extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_EMPLOYEE_ID = "employeeId";
    public static final String EXTRA_COMPANY_ID = "companyId";
    private RecyclerView employeesRecycleView;
    private EmployeeViewModel employeeViewModel;
    EmployeeAdapter employeeAdapter;
    Long idCompany;

    List<User> employees =  new LinkedList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_company_profile);
        Intent intent = getIntent();
        idCompany = intent.getLongExtra(EXTRA_COMPANY_ID, 0);

        Log.d("EXTRA_COMPANY_ID", String.valueOf(idCompany));
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_admin);

        employeesRecycleView = findViewById(R.id.admin_operators);
        employeesRecycleView.setHasFixedSize(true);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        employeeViewModel.init(idCompany);
        employeeViewModel.getEmployeeRepository().observe(this, response -> {
            employees.clear();
            employees.addAll(response.getUsers());
            employeeAdapter.notifyDataSetChanged();
            Log.d("Employees List:", employees.toString());
           Log.d("Employees: ", "" + employeeAdapter.getItemCount());
        });
        setUpRecyclerView();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyEmployees.this, EmployeeProfile.class);
                intent.putExtra(EXTRA_COMPANY_ID, idCompany);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        if (employeeAdapter == null) {
            employeeAdapter = new EmployeeAdapter(CompanyEmployees.this, employees);
            employeesRecycleView.setLayoutManager(new LinearLayoutManager(this));
            employeesRecycleView.setAdapter(employeeAdapter);
            employeesRecycleView.setItemAnimator(new DefaultItemAnimator());
            employeesRecycleView.setNestedScrollingEnabled(true);
            employeeAdapter.setOnItemClickListener(CompanyEmployees.this);
        } else {
            employeeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        User clickeduser = employees.get(position);
        Log.d("ClickedUser: ", employees.get(position).toString());
        Intent intent = new Intent(this, EmployeeProfile.class);
        intent.putExtra(EXTRA_COMPANY_ID, idCompany);
        intent.putExtra(EXTRA_EMPLOYEE_ID, clickeduser.getId());
        startActivity(intent);
    }
}



