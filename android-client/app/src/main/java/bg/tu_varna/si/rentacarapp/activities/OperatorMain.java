package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import bg.tu_varna.si.rentacarapp.Cars;
import bg.tu_varna.si.rentacarapp.Clients;
import bg.tu_varna.si.rentacarapp.Contracts;
import bg.tu_varna.si.rentacarapp.Queries;
import bg.tu_varna.si.rentacarapp.R;

public class OperatorMain extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_COMPANY_ID = "companyId";

    private CardView getAllContracts;
    private CardView getAllClients;
    private CardView getAllVehicles;
    private CardView getReports;
    Intent intent;
    private long companyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_main);
        getAllContracts = findViewById(R.id.contract);
        getAllClients = findViewById(R.id.clients);
        getAllVehicles = findViewById(R.id.cars);
        getReports = findViewById(R.id.queries);

        getAllContracts.setOnClickListener(this);
        getAllClients.setOnClickListener(this);
        getAllVehicles.setOnClickListener(this);
        getReports.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.contract:
                intent = new Intent(OperatorMain.this, Contracts.class);
                startActivity(intent);
                break;
            case R.id.clients:
                intent = new Intent(OperatorMain.this, Clients.class);
                startActivity(intent);
                break;
            case R.id.cars:
                intent = new Intent(OperatorMain.this, Cars.class);
                startActivity(intent);
                break;
            case R.id.queries:
                intent = new Intent(OperatorMain.this, Queries.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}

