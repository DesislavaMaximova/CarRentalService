package bg.tu_varna.si.rentacarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import bg.tu_varna.si.rentacarapp.CompanyEmployees;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.AdminService;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeProfile extends AppCompatActivity {

    public static final String EXTRA_COMPANY_ID = "companyId";
    public static final String EXTRA_EMPLOYEE_ID = "employeeId";

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    ImageView image;
    Long idCompany;
    Long idEmployee;
    JSONObject jsonObjectUser;
    RequestQueue requestQueue;
    AdminService adminService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        FloatingActionButton fabCheck = findViewById(R.id.fab_check);
        FloatingActionButton fabUpdate = findViewById(R.id.fab_update);
        FloatingActionButton fabBack = findViewById(R.id.fab_back);
        editTextUsername = findViewById(R.id.edit_usernameEmployee);
        editTextPassword = findViewById(R.id.edit_password);
        editTextFirstName = findViewById(R.id.edit_firstName);
        editTextLastName = findViewById(R.id.edit_lastName);
        editTextEmail = findViewById(R.id.edit_email);
        image = findViewById(R.id.imageView);
        Intent intent = getIntent();
        idCompany = intent.getLongExtra(EXTRA_COMPANY_ID, 0);
        Log.d("CompanyId:", idCompany.toString());
        idEmployee = intent.getLongExtra(EXTRA_EMPLOYEE_ID, -1);
        Log.d("EmployeeId: ", idEmployee.toString());
        if (intent.hasExtra(EXTRA_EMPLOYEE_ID)) {
            setTitle("Edit Profile");
            adminService = RetrofitService.cteateService(AdminService.class);
            Call<User> call = adminService.getUser(JwtHandler.getJwt(), idEmployee);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.d("Response", response.message().toString());
                        User user = new User(Role.OPERATOR);
                        user.setFirstName(response.body().getFirstName());
                        user.setLastName(response.body().getLastName());
                        user.setUsername(response.body().getUsername());
                        user.setPassword(response.body().getPassword());
                        user.setEmail(response.body().getEmail());
                        editTextFirstName.setText(user.getFirstName());
                        editTextLastName.setText(user.getLastName());
                        editTextEmail.setText(user.getEmail());
                        editTextUsername.setText(user.getUsername());
                        editTextPassword.setText(user.getPassword());
                    } else {
                        Toast.makeText(EmployeeProfile.this, "Employee's profile can't be loaded", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EmployeeProfile.this, "Employee's profile can't be loaded", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            setTitle("Create new Employee profile");
            fabUpdate.hide();
        }

        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEmployee();
           setResult(RESULT_OK);
           finish();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }


    private void postEmployee() {
        User user = new User(Role.OPERATOR);
        user.setFirstName(editTextFirstName.getText().toString());
        user.setLastName(editTextLastName.getText().toString());
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        adminService = RetrofitService.cteateService(AdminService.class);
        Call<User> call = adminService.createEmployee(JwtHandler.getJwt(), idCompany, user);
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
//
//    public void fillJSON() {
//        String username = editTextUsername.getText().toString();
//        String password = editTextPassword.getText().toString();
//        String firstName = editTextFirstName.getText().toString();
//        String lastName = editTextLastName.getText().toString();
//        String email = editTextEmail.getText().toString();
//        User user = new User(Role.OPERATOR);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        AuthenticationRequest auth = new AuthenticationRequest(username, password);
//        UserRequest userRequest = new UserRequest(user, auth);
//
//
//        try {
//            jsonObjectUser = new JSONObject(new Gson().toJson(userRequest));
//            Log.d("hmz", jsonObjectUser.toString(2));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public void putJSON() {
//        fillJSON();
//        String epr = String.format(endpointUsers, idCompany, idEmployee);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, epr, jsonObjectUser,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        User employee = new Gson().fromJson(String.valueOf(response), User.class);
//                        Toast.makeText(EmployeeProfile.this, "Employee Profile is updated",
//                                Toast.LENGTH_LONG).show();
//                        Log.d("put method: ", String.valueOf(response));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText(EmployeeProfile.this, "Employee Profile can't be updated",
//                        Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new LinkedHashMap<>();
//                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
//                return header;
//            }
//        };
//        requestQueue.add(request);
    }


