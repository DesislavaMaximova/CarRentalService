package bg.tu_varna.si.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.User;
import bg.tu_varna.si.rentacarapp.activities.CarNew;
import bg.tu_varna.si.rentacarapp.activities.EmployeeProfile;
import bg.tu_varna.si.rentacarapp.activities.MainActivity;
import bg.tu_varna.si.rentacarapp.adapter.CarAdapter;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.adapter.EmployeeAdapter;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.viewModels.CarViewModel;

public class Cars extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
    public static final String EXTRA_CAR_ID = "carId";
    private RecyclerView carsRecycleView;
    private CarViewModel carViewModel;
    private CarAdapter carAdapter;
    List<Car> cars = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        FloatingActionButton fabAdd = findViewById(R.id.fab_cars_add);
        carsRecycleView = findViewById(R.id.recyclerView_cars);
        carsRecycleView.setHasFixedSize(true);
        carViewModel = new ViewModelProvider(this).get(CarViewModel.class);
        carViewModel.init(CompanyId.getCompanyId());
        Log.d("CompanyId: ", String.valueOf(CompanyId.getCompanyId()));
        carViewModel.getAllCarsObservable().observe(this, response -> {
            Log.d("Cars count: ", String.valueOf(cars.size()));
            cars.clear();
            cars.addAll(response.getCars());
            carAdapter.notifyDataSetChanged();
        });
        setUpRecyclerView();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cars.this, CarNew.class);
                startActivity(intent);
            }
        });
    }
        private void setUpRecyclerView() {
            if (carAdapter == null) {
                carAdapter = new CarAdapter(Cars.this, cars);
                carsRecycleView.setLayoutManager(new LinearLayoutManager(this));
                carsRecycleView.setAdapter(carAdapter);
                carsRecycleView.setItemAnimator(new DefaultItemAnimator());
                carsRecycleView.setNestedScrollingEnabled(true);
                carAdapter.setOnItemClickListener(Cars.this);
            } else {
                carAdapter.notifyDataSetChanged();
            }
        }
    public void onItemClick(int position) {
        Car clickedCar = cars.get(position);
        Log.d("ClickedItem: ", cars.get(position).toString());
        Intent intent = new Intent(this, CarNew.class);
        intent.putExtra(EXTRA_CAR_ID, clickedCar.getCarId());
        startActivity(intent);
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
