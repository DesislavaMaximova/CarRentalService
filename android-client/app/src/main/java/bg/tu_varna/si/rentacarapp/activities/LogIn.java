package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import bg.tu.varna.si.model.auth.AuthenticationResponse;
import bg.tu_varna.si.rentacarapp.AdministratorAllCompanies;
import bg.tu_varna.si.rentacarapp.Constants;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.VolleySingelton;

public class LogIn extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
        RequestQueue requestQueue;
    String endpointLogin = Constants.BACKEND_ADDRESS + Constants.AUTH + "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button_submit);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_pass);


         username.setText("filip");
        password.setText("filip");

        login.setOnClickListener(v -> {
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }


    private void login() throws JSONException {
        if (username.equals("") || password.equals("")) {
            Toast.makeText(LogIn.this, "Invalid username and password! ", Toast.LENGTH_LONG).show();
        } else {

            JSONObject object = new JSONObject();
            object.put("username", username.getText().toString());
            object.put("password", password.getText().toString());


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, endpointLogin,
                    object, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    AuthenticationResponse authenticationResponse =
                            new Gson().fromJson(String.valueOf(response), AuthenticationResponse.class);

                    JwtHandler.setJwt(authenticationResponse.getJwt());

                    Toast.makeText(LogIn.this, "Welcome : " + authenticationResponse.getRole(), Toast.LENGTH_LONG).show();

                    Log.d("JSON", String.valueOf(response));
                    if(authenticationResponse.getRole().equals("ADMINISTRATOR")) {

                        Intent intent = new Intent(LogIn.this, AdministratorAllCompanies.class);

                        startActivity(intent);
                    } else if(authenticationResponse.getRole().equals("OPERATOR")){
                        Intent intent = new Intent(LogIn.this, OperatorMain.class);
                        startActivity(intent);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", "login failed: " + endpointLogin, error );
                    error.printStackTrace();
                    Toast.makeText(LogIn.this, "Invalid username or password! ", Toast.LENGTH_LONG).show();
                }
            });


            requestQueue = VolleySingelton.getInstance(this).getRequestQueue();
            requestQueue.add(request);
        }
    }
}