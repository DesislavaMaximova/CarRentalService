package bg.tu_varna.si.rentacarapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import bg.tu.varna.si.model.ClientList;
import bg.tu_varna.si.rentacarapp.repositories.ClientRepository;

public class ClientViewModel extends ViewModel {
    private LiveData<ClientList> allClientsObservable;
    private ClientRepository clientRepository;

    public void init(long companyId){
        if(allClientsObservable !=null){
            return;
        }
        clientRepository=ClientRepository.getInstance();
        allClientsObservable=clientRepository.getClients(companyId);
    }
    public LiveData<ClientList> getAllClientsObservable (){
        return allClientsObservable;
    }
}
