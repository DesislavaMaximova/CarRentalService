package bg.tu_varna.si.rentacarapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.model.ClientList;
import bg.tu_varna.si.rentacarapp.service.ApiService;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientRepository {
    private ApiService apiService;
    private static ClientRepository clientRepository;

    public static ClientRepository getInstance() {
        if (clientRepository == null) {
            clientRepository = new ClientRepository();
        }
        return clientRepository;
    }

    public ClientRepository() {
        apiService = RetrofitService.cteateService(ApiService.class);
    }

    public LiveData<ClientList> getClients(long companyId) {
        MutableLiveData<ClientList> clientsListMutableLiveData = new MutableLiveData<>();
        apiService.getAllClients(JwtHandler.getJwt(), companyId).enqueue(new Callback<ClientList>() {
            @Override
            public void onResponse(Call<ClientList> call, Response<ClientList> response) {
                clientsListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ClientList> call, Throwable t) {
                clientsListMutableLiveData.setValue(null);
                Log.d("Error: ", t.getMessage());
            }
        });

        return clientsListMutableLiveData;
    }
}
