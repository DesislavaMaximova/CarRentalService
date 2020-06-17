package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import bg.tu.varna.si.model.auth.AuthenticationRequest;
import bg.tu.varna.si.model.auth.AuthenticationResponse;
import bg.tu_varna.si.rentacarapp.AdministratorAllCompanies;
import bg.tu_varna.si.rentacarapp.CompanyEmployees;
import bg.tu_varna.si.rentacarapp.Constants;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.AuthService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import bg.tu_varna.si.rentacarapp.service.VolleySingelton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static long userId;

    private EditText editUsername;
    private EditText editPassword;
    private Button login;
    AuthService authService;
    private AuthenticationRequest credentials;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button_submit);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_pass);


//        username.setText("");
//        password.setText("");


        login.setOnClickListener(v -> {
            credentials = new AuthenticationRequest();
            credentials.setUsername(editUsername.getText().toString());
            Log.d("Username: ", editUsername.getText().toString());
            credentials.setPassword(editPassword.getText().toString());
            authService = RetrofitService.cteateService(AuthService.class);

            Call<AuthenticationResponse> call = authService.login(credentials);
            call.enqueue(new Callback<AuthenticationResponse>() {
                @Override
                public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                    if(response.isSuccessful()) {
                        Log.d("Response", String.valueOf(response.code()));
                        JwtHandler.setJwt(response.body().getJwt());
                        CompanyId.setCompanyId(response.body().getCompanyId());
                        Log.d("Role: ", response.body().getRole().toString());
                        Toast.makeText(MainActivity.this, "Welcome : " + response.body().getRole(), Toast.LENGTH_LONG).show();
                        if (response.body().getRole().equals("ADMINISTRATOR")) {
                            Intent intent = new Intent(MainActivity.this, AdministratorAllCompanies.class);
                            startActivity(intent);
                        } else if (response.body().getRole().equals("OPERATOR")) {
                            Intent intent = new Intent(MainActivity.this, OperatorMain.class);
                            userId = response.body().getUserId();
                         //   intent.putExtra(EXTRA_USERNAME, response.body().getUsername());
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Unable to log in: " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        });

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}
