package bg.tu_varna.si.rentacarapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import bg.tu.varna.si.model.UserList;
import bg.tu_varna.si.rentacarapp.service.AdminService;
import bg.tu_varna.si.rentacarapp.service.JwtHandler;
import bg.tu_varna.si.rentacarapp.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {
    private AdminService employeeService;
    private static EmployeeRepository employeRepository;

    public static EmployeeRepository getInstance() {
        if (employeRepository == null) {
          employeRepository = new EmployeeRepository();
        }
        return employeRepository;
    }
    public EmployeeRepository() {
        employeeService = RetrofitService.cteateService(AdminService.class);
    }
    public LiveData<UserList> getEmployees(long companyId) {
        MutableLiveData<UserList> employeeListMutableLiveData = new MutableLiveData<>();
        employeeService.getAllEmployees(JwtHandler.getJwt(), companyId).enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                employeeListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                employeeListMutableLiveData.setValue(null);
                Log.d("Error: ", t.getMessage());
            }
        });

        return employeeListMutableLiveData;
    }
}
