package bg.tu_varna.si.rentacarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bg.tu.varna.si.model.CompanyList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu_varna.si.rentacarapp.activities.CompanyNew;
import bg.tu_varna.si.rentacarapp.activities.EmployeeProfile;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.adapter.EmployeeAdapter;
import bg.tu_varna.si.rentacarapp.service.AdminService;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import bg.tu_varna.si.rentacarapp.service.VolleySingelton;
import bg.tu_varna.si.rentacarapp.viewModels.CompanyViewModel;
import bg.tu_varna.si.rentacarapp.viewModels.EmployeeViewModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static bg.tu_varna.si.rentacarapp.AdministratorAllCompanies.EXTRA_COMPANY_ID;
import static bg.tu_varna.si.rentacarapp.R.layout.activity_company_profile;

public class CompanyEmployees extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_EMPLOYEE_ID = "employeeId";
    public static final String EXTRA_COMPANY_ID = "companyId";
    // private String url = Constants.BACKEND_ADDRESS + Constants.ADMIN + "companies";
    private RecyclerView employeesRecycleView;
    private EmployeeViewModel employeeViewModel;
    UserList employeesList;
    private TextView textViewCompanyName;
    EmployeeAdapter employeeAdapter;
    private RequestQueue requestQueue;
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
            employees.addAll(response.getUsers());
            employeeAdapter.notifyDataSetChanged();
            Log.d("DEBUG", "" + employeeAdapter.getItemCount());
        });
        setUpRecyclerView();
        AdminService adminService = RetrofitService.cteateService(AdminService.class);
        Call<UserList> call = adminService.getAllEmployees(JwtHandler.getJwt(), idCompany);
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CompanyEmployees.this, "Employees list can't be loaded! ", Toast.LENGTH_LONG);
                    Log.d("Error code: ", String.valueOf(response.code()));
                    return;
                }
                employeesList = response.body();
                Log.d("ListEmployees:", String.valueOf(response.body()));

                employees = new LinkedList<>();
                for (User user : employeesList.getUsers()) {
                    employees.add(user);
                }

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(CompanyEmployees.this, "Employees list can't be loaded! ", Toast.LENGTH_LONG);
            }
        });

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
        User clickeduser = employeesList.getUsers().get(position);

        Intent intent = new Intent(this, EmployeeProfile.class);
        intent.putExtra(EXTRA_COMPANY_ID, idCompany);
        intent.putExtra(EXTRA_EMPLOYEE_ID, clickeduser.getId());
        startActivity(intent);
    }
}



