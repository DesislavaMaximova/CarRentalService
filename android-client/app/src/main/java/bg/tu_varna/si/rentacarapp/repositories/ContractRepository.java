package bg.tu_varna.si.rentacarapp.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bg.tu.varna.si.model.Contract;
import bg.tu.varna.si.model.ContractList;
import bg.tu_varna.si.rentacarapp.activities.Client_new;
import bg.tu_varna.si.rentacarapp.activities.ContractNew;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.CompanyId;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractRepository {
    private ApiService apiService;
    private static ContractRepository contractRepository;

    public ContractRepository() {
        apiService = RetrofitService.cteateService(ApiService.class);
    }

    public static ContractRepository getInstance() {
        if (contractRepository == null) {
            contractRepository = new ContractRepository();
        }
        return contractRepository;
    }

    public LiveData<ContractList> getAllContracts(long companyId) {
        MutableLiveData<ContractList> contractListMutableLiveData = new MutableLiveData<>();
        apiService.getAllContracts(JwtHandler.getJwt(),companyId).enqueue(new Callback<ContractList>() {
            @Override
            public void onResponse(Call<ContractList> call, Response<ContractList> response) {
                contractListMutableLiveData.setValue(response.body());
                Log.d("Contracts", response.body().toString());
            }

            @Override
            public void onFailure(Call<ContractList> call, Throwable t) {
                contractListMutableLiveData.setValue(null);
                Log.d("Error: ", t.getMessage());

            }
        });
        return contractListMutableLiveData;
    }
}
