package bg.tu_varna.si.rentacarapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bg.tu.varna.si.model.CompanyList;
import bg.tu_varna.si.rentacarapp.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyRepository {
    private CompanyService companyService;
    private static CompanyRepository companyRepository;

    public static CompanyRepository getInstance() {
        if (companyRepository == null) {
            companyRepository = new CompanyRepository();
        }
        return companyRepository;
    }

    public CompanyRepository() {
       companyService = RetrofitService.cteateService(CompanyService.class);
    }

    public LiveData<CompanyList> getCompanies() {
       MutableLiveData<CompanyList> companyListMutableLiveData = new MutableLiveData<>();
        companyService.getAllCompanies().enqueue(new Callback<CompanyList>() {
            @Override
            public void onResponse(Call<CompanyList> call, Response<CompanyList> response) {
                companyListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CompanyList> call, Throwable t) {
                companyListMutableLiveData.setValue(null);
                Log.d("Error: ", t.getMessage());
            }
        });

        return companyListMutableLiveData;
    }
}
