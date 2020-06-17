package bg.tu_varna.si.rentacarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import bg.tu.varna.si.model.Role;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserRequest;
import bg.tu_varna.si.rentacarapp.AdministratorAllCompanies;
import bg.tu_varna.si.rentacarapp.CompanyEmployees;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.AdminService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeProfile extends AppCompatActivity {

  //  public static final String EXTRA_COMPANY_ID = "companyId";
    public static final String EXTRA_EMPLOYEE_ID = "employeeId";

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    ImageView image;
    //Long idCompany;
    Long idEmployee;
    AdminService adminService;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        FloatingActionButton fabCheck = findViewById(R.id.fab_check);
        FloatingActionButton fabBack = findViewById(R.id.fab_back);
        editTextUsername = findViewById(R.id.edit_usernameEmployee);
        editTextPassword = findViewById(R.id.edit_password);
        editTextFirstName = findViewById(R.id.edit_firstName);
        editTextLastName = findViewById(R.id.edit_lastName);
        editTextEmail = findViewById(R.id.edit_email);
        image = findViewById(R.id.imageView);
        Intent intent = getIntent();
       // idCompany = intent.getLongExtra(EXTRA_COMPANY_ID, 0);
       // Log.d("CompanyId:", idCompany.toString());
        idEmployee = intent.getLongExtra(EXTRA_EMPLOYEE_ID, -1);
        Log.d("EmployeeId: ", idEmployee.toString());
        adminService = RetrofitService.cteateService(AdminService.class);


        if (idEmployee != -1) {
            setTitle("Edit profile");
            getEmployee();
            fabCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Company ID: ", String.valueOf(CompanyId.getCompanyId()));
                    putEmployee(fillUser(), CompanyId.getCompanyId(), idEmployee);
                    handler.postDelayed(updateTimeTask, 1500);
                }
            });
        } else {
            setTitle("New employee ");

            fabCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postEmployee(fillUser(), CompanyId.getCompanyId());
                    handler.postDelayed(updateTimeTask, 1500);
                }
            });

        }

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }


    private void postEmployee(User user, long idCompany) {

        Call<User> call = adminService.createEmployee(JwtHandler.getJwt(), idCompany, user);
        Log.d("User: ", user.toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(EmployeeProfile.this, "Employee profile created!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EmployeeProfile.this, "Employee profile can't be created!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(EmployeeProfile.this, CompanyEmployees.class);
           // intent.putExtra(EXTRA_COMPANY_ID, idCompany);
            startActivity(intent);
        }
    };

    private void putEmployee(User user, long companyId, long employeeId) {
        Call<User> call = adminService.udapateEmployee(JwtHandler.getJwt(),companyId, employeeId, user);
        //Log.d("companyId: ", idCompany.toString());
        Log.d("employeeId: ", idEmployee.toString());
        Log.d("User: ", user.toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.message().toString());

                } else {
                    Toast.makeText(EmployeeProfile.this, "Employee's profile can't be loaded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EmployeeProfile.this, "Employee's profile can't be loaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEmployee() {
        Call<User> call = adminService.getUser(JwtHandler.getJwt(), idEmployee);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                editTextUsername.setText(response.body().getUsername());
                editTextPassword.setText(response.body().getPassword());
                editTextFirstName.setText(response.body().getFirstName());
                editTextLastName.setText(response.body().getLastName());
                editTextEmail.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private User fillUser() {
        User user = new User(Role.OPERATOR);
        user.setFirstName(editTextFirstName.getText().toString());
        user.setLastName(editTextLastName.getText().toString());
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        user.setCompanyId(CompanyId.getCompanyId());
        return user;

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


