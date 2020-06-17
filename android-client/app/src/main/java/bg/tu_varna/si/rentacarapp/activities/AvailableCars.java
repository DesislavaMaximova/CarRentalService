package bg.tu_varna.si.rentacarapp.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import bg.tu_varna.si.rentacarapp.Cars;
import bg.tu_varna.si.rentacarapp.R;
import bg.tu_varna.si.rentacarapp.adapter.CarAdapter;
import bg.tu_varna.si.rentacarapp.adapter.CompanyAdapter;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.viewModels.CarViewModel;

public class AvailableCars extends AppCompatActivity implements CompanyAdapter.OnItemClickListener {
   public static final String EXTRA_CAR_ID = "carId";
 private RecyclerView carsRecycleView;
   private CarViewModel carViewModel;
    private CarAdapter carAdapter;
   List<Car> cars = new LinkedList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cars);

       carsRecycleView = findViewById(R.id.recyclerView_availableCars);
       carsRecycleView.setHasFixedSize(true);
       carViewModel = new ViewModelProvider(this).get(CarViewModel.class);
     carViewModel.available(CompanyId.getCompanyId());
     Log.d("CompanyId: ", String.valueOf(CompanyId.getCompanyId()));
      carViewModel.getAllAvailableCarsObservable().observe(this, response -> {
          Log.d("Cars count: ", String.valueOf(cars.size()));
           cars.clear();
         cars.addAll(response.getCars());
          carAdapter.notifyDataSetChanged();
      });
        setUpRecyclerView();

   }


   private void setUpRecyclerView() {
       if (carAdapter == null) {
            carAdapter = new CarAdapter(AvailableCars.this, cars);
          carsRecycleView.setLayoutManager(new LinearLayoutManager(this));
          carsRecycleView.setAdapter(carAdapter);
        carsRecycleView.setNestedScrollingEnabled(true);
           carAdapter.setOnItemClickListener(AvailableCars.this);
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