package bg.tu_varna.si.rentacarapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bg.tu.varna.si.model.CarList;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarRepository {
    private ApiService apiService;
    private static CarRepository carRepository;

    public static CarRepository getInstance() {
        if (carRepository == null) {
            carRepository = new CarRepository();
        }
        return carRepository;
    }

    public CarRepository() {
        apiService = RetrofitService.cteateService(ApiService.class);
    }

    public LiveData<CarList> getCars(long companyId) {
        MutableLiveData<CarList> carListMutableLiveData = new MutableLiveData<>();
        apiService.getAllCars(JwtHandler.getJwt(), companyId).enqueue(new Callback<CarList>() {
            @Override
            public void onResponse(Call<CarList> call, Response<CarList> response) {
                carListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CarList> call, Throwable t) {
                carListMutableLiveData.setValue(null);
                Log.d("Error: ", t.getMessage());
            }
        });
        return carListMutableLiveData;
    }


}
