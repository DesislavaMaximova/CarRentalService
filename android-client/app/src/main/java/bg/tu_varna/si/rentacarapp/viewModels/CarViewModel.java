package bg.tu_varna.si.rentacarapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarList;
import bg.tu_varna.si.rentacarapp.repositories.CarRepository;

public class CarViewModel extends ViewModel {
    LiveData<CarList> allCarsObservable;
    private CarRepository carRepository;

    public void init(long companyId){
        if(allCarsObservable !=null){
            return;
        }
        carRepository=CarRepository.getInstance();
        allCarsObservable = carRepository.getCars(companyId);
    }

    public LiveData<CarList> getAllCarsObservable(){
        return allCarsObservable;
    }
}
