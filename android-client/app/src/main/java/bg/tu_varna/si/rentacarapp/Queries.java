package bg.tu_varna.si.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import bg.tu_varna.si.rentacarapp.R;
//import bg.tu_varna.si.rentacarapp.activities.AvailableCars;
import bg.tu_varna.si.rentacarapp.activities.AvailableCars;
import bg.tu_varna.si.rentacarapp.activities.CarRentalHistory;
import bg.tu_varna.si.rentacarapp.activities.ClientsByRating;
import bg.tu_varna.si.rentacarapp.activities.OperatorMain;
import bg.tu_varna.si.rentacarapp.activities.Statistics;

public class Queries extends AppCompatActivity implements View.OnClickListener{
    private CardView getAllAvailableCars;
    private CardView getAllCarsRentalHistory;
    private CardView getAllClientsByRating;
    private CardView getCarStatistics;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        getAllAvailableCars = findViewById(R.id.available_cars);
        getAllCarsRentalHistory = findViewById(R.id.car_rental_history);
        getAllClientsByRating = findViewById(R.id.clients_rating);
        getCarStatistics = findViewById(R.id.statistics);

        getAllAvailableCars.setOnClickListener(this);
        getAllCarsRentalHistory.setOnClickListener(this);
        getAllClientsByRating.setOnClickListener(this);
        getCarStatistics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.available_cars:
                intent = new Intent(Queries.this, AvailableCars.class);
                startActivity(intent);
                break;
            case R.id.car_rental_history:
                intent = new Intent(Queries.this, CarRentalHistory.class);
                startActivity(intent);
                break;
            case R.id.clients_rating:
                intent = new Intent(Queries.this, ClientsByRating.class);
                startActivity(intent);
                break;
            case R.id.statistics:
                intent = new Intent(Queries.this, Statistics.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}



