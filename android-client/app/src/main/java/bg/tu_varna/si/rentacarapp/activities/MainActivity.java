package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.VolleySingelton;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    AlertDialog.Builder builder;
    RequestQueue requestQueue;
    String url = "http://192.168.100.3:8080/auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.button_submit);
        username= findViewById(R.id.edit_username);
        password= findViewById(R.id.edit_pass);
        builder = new AlertDialog.Builder(this);

        username.setText("admin");
        password.setText("admin");

        login.setOnClickListener(v -> {
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }


    private void login() throws JSONException {

        JSONObject object = new JSONObject();
        object.put("username", username.getText().toString());
        object.put("password", password.getText().toString());


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jwt = response.getString("jwt");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "String Response : " + response.toString(), Toast.LENGTH_LONG).show();

                Log.d("JSON", String.valueOf(response));
                Intent intent = new Intent(MainActivity.this, OperatorMain.class);

                startActivity(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        requestQueue = VolleySingelton.getInstance(this).getRequestQueue();
        requestQueue.add(request);
    }
}



