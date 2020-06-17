package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import bg.tu.varna.si.model.Company;
import bg.tu_varna.si.rentacarapp.AdministratorAllCompanies;
import bg.tu_varna.si.rentacarapp.Constants;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.VolleySingelton;

public class CompanyNew extends AppCompatActivity {
    private EditText companyName;
    private Button createCompany;
    private String url = Constants.BACKEND_ADDRESS + Constants.ADMIN +"companies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);
        companyName = findViewById(R.id.company_name);
        createCompany = findViewById(R.id.button_create);

        createCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = new JSONObject();
                try {
                    object.put("name", companyName.getText().toString());
                    companyName.setText("");
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                            object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Company company =
                                    new Gson().fromJson(String.valueOf(response), Company.class);
                            Toast.makeText(CompanyNew.this, "Company created!" , Toast.LENGTH_LONG);
                            Log.d("JSON", String.valueOf(response));
                            Intent intent = new Intent(CompanyNew.this, AdministratorAllCompanies.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> header = new LinkedHashMap<>();
                            header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                            return header;
                        }
                    };
                    RequestQueue  requestQueue = VolleySingelton.getInstance(CompanyNew.this).getRequestQueue();
                    requestQueue.add(request);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
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


