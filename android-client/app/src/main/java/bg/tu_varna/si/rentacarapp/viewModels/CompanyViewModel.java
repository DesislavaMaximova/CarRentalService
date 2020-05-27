package bg.tu_varna.si.rentacarapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import bg.tu.varna.si.model.CompanyList;
import bg.tu_varna.si.rentacarapp.repositories.CompanyRepository;

public class CompanyViewModel extends ViewModel {

    private LiveData<CompanyList> allCompaniesObservable;
    private CompanyRepository companyRepository;

    public void init(){
        if (allCompaniesObservable != null){
            return;
        }
        companyRepository = CompanyRepository.getInstance();
        allCompaniesObservable = companyRepository.getCompanies();
    }
    public LiveData<CompanyList> getCompanyRepository() {
        return allCompaniesObservable;
    }

}
