package bg.tu_varna.si.rentacarapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import bg.tu.varna.si.model.ContractList;
import bg.tu_varna.si.rentacarapp.repositories.ContractRepository;

public class ContractViewModel extends ViewModel {
    LiveData<ContractList> allContactsObservable;
    private ContractRepository contractRepository;

    public void init(long companyId) {
        if (allContactsObservable != null) {
            return;
        }
        contractRepository = ContractRepository.getInstance();
        allContactsObservable = contractRepository.getAllContracts(companyId);
    }

    public LiveData<ContractList> getAllContactsObservable () {return allContactsObservable;}
}
