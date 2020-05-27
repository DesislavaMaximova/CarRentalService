package bg.tu_varna.si.rentacarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;

import static bg.tu_varna.si.rentacarapp.AdministratorAllCompanies.EXTRA_COMPANY_ID;
import static bg.tu_varna.si.rentacarapp.R.layout.activity_company_profile;

public class CompanyEmployees extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_ACTION = "new";
    public static final String EXTRA_EMPLOYEE_ID = "id";
    private String url = Constants.BACKEND_ADDRESS + Constants.ADMIN + "companies";
    public static RecyclerView employeesRecycleView;
    UserList employeesList = new UserList();
    private TextView textViewCompanyName;
    EmployeeAdapter employeeAdapter;
    private RequestQueue requestQueue;
    Long idCompany;


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
        employeesRecycleView.setLayoutManager(new LinearLayoutManager(this));


        parseJSON();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyEmployees.this, EmployeeProfile.class);
                intent.putExtra(EXTRA_COMPANY_ID, idCompany);
                intent.putExtra(EXTRA_ACTION, true);
                startActivity(intent);
            }
        });
    }

    private void parseJSON() {
        String endpoint = url + "/%s" + "/users";
        String epr = String.format(endpoint, idCompany);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, epr,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                employeesList = new Gson().fromJson(String.valueOf(response), (Type) UserList.class);
                List<User> employees = employeesList.getUsers();
                if(employees == null){
                    employees = Collections.emptyList();
                }
                employeeAdapter = new EmployeeAdapter(CompanyEmployees.this, employees);
                employeesRecycleView.setAdapter(employeeAdapter);
                employeeAdapter.setOnItemClickListener(CompanyEmployees.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<>();
                header.put("Authorization", "Bearer " + JwtHandler.getJwt());
                return header;
            }

        };
        requestQueue = VolleySingelton.getInstance(this).getRequestQueue();
        requestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {
        User clickeduser = employeesList.getUsers().get(position);

        Intent intent = new Intent(this, EmployeeProfile.class);
        intent.putExtra(EXTRA_COMPANY_ID, idCompany);
        intent.putExtra(EXTRA_ACTION, false);
        intent.putExtra(EXTRA_EMPLOYEE_ID, clickeduser.getId());
        startActivity(intent);
    }
}



