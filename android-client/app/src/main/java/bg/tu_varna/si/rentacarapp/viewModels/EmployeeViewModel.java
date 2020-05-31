package bg.tu_varna.si.rentacarapp.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.si.model.CompanyList;
import bg.tu.varna.si.model.User;
import bg.tu.varna.si.model.UserList;
import bg.tu_varna.si.rentacarapp.repositories.EmployeeRepository;

public class EmployeeViewModel extends ViewModel {
    private LiveData<UserList> allEmployeesObservable;
    private EmployeeRepository employeeRepository;

    public void init(long idCompany) {
        if (allEmployeesObservable != null) {
            return;
        }
        employeeRepository = EmployeeRepository.getInstance();
        allEmployeesObservable = employeeRepository.getEmployees(idCompany);
    }

    public LiveData<UserList> getEmployeeRepository()
    {
        return allEmployeesObservable;
    }

}
