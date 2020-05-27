package bg.tu_varna.si.rentacarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import bg.tu.varna.si.model.Role;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserRequest;
import bg.tu.varna.si.model.auth.AuthenticationRequest;

public class EmployeeProfile extends AppCompatActivity {
    public static final String EXTRA_ACTION = "new";
    public static final String EXTRA_COMPANY_ID = "companyId";
    public static final String EXTRA_EMPLOYEE_ID = "id";

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    ImageView image;


    String endpointCompanies = Constants.BACKEND_ADDRESS + Constants.ADMIN + "companies";
    String endpointUsers = Constants.BACKEND_ADDRESS + Constants.API + "users/" + "%s";
    String endpointCompanyUsers = endpointCompanies + "/%s" + "/users";
    String endpointCompanyEmployee = endpointCompanyUsers + "/%s";


    Long idCompany;
    Long idEmployee;
    JSONObject jsonObjectUser;
    RequestQueue requestQueue;


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
        idEmployee = intent.getLongExtra(EXTRA_EMPLOYEE_ID, -1);

        requestQueue = VolleySingelton.getInstance(this).getRequestQueue();
        if (intent.hasExtra(EXTRA_EMPLOYEE_ID)) {
            setTitle("Edit Profile");
            getJson();


        } else {
            setTitle("Create new Employee profile");
            fabUpdate.hide();
        }
        Log.d("Id employee", String.valueOf(idEmployee));

        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJSON();
                setResult(RESULT_OK);
                finish();
            }
        });
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putJSON();
                setResult(RESULT_OK);
                finish();
            }
        });
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EmployeeProfile.this, CompanyEmployees.class);
                startActivity(intent1);
            }
        });
    }


    private void postJSON() {
        fillJSON();

        String epr = String.format(endpointCompanyUsers, idCompany);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                epr, jsonObjectUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User employee = new Gson().fromJson(String.valueOf(response), User.class);

                        Toast.makeText(EmployeeProfile.this, "Employee Profile is created",
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EmployeeProfile.this, "Employee Profile can't be created",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                return header;
            }

        };

        requestQueue.add(request);
    }

    public void fillJSON() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        User user = new User(Role.OPERATOR);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        AuthenticationRequest auth = new AuthenticationRequest(username, password);
        UserRequest userRequest = new UserRequest(user, auth);


        try {
            jsonObjectUser = new JSONObject(new Gson().toJson(userRequest));
            Log.d("hmz", jsonObjectUser.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getJson() {
        String epr = String.format(endpointUsers, idEmployee);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                epr, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User employee = new Gson().fromJson(String.valueOf(response), User.class);
                        Log.d("hmz", String.valueOf(employee));

                        Toast.makeText(EmployeeProfile.this, "Employee Profile is loaded",
                                Toast.LENGTH_LONG).show();
                        editTextUsername.setText(employee.getUsername());
                        editTextFirstName.setText(employee.getFirstName());
                        editTextLastName.setText(employee.getLastName());
                        editTextEmail.setText(employee.getEmail());
                        editTextPassword.setText(employee.getPassword());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EmployeeProfile.this, "Employee Profile can't be loaded",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                return header;
            }
        };

        requestQueue.add(request);
        Log.d("request", String.valueOf(request));
    }

    public void putJSON() {
        fillJSON();
        String epr = String.format(endpointUsers, idCompany, idEmployee);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, epr, jsonObjectUser,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User employee = new Gson().fromJson(String.valueOf(response), User.class);
                        Toast.makeText(EmployeeProfile.this, "Employee Profile is updated",
                                Toast.LENGTH_LONG).show();
                        Log.d("put method: ", String.valueOf(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EmployeeProfile.this, "Employee Profile can't be updated",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                return header;
            }
        };
        requestQueue.add(request);
    }
}
