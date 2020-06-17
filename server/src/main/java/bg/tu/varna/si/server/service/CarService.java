package bg.tu.varna.si.server.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.model.Car;
import bg.tu.varna.si.model.CarList;
import bg.tu.varna.si.server.db.entity.CarEntity;
import bg.tu.varna.si.server.db.entity.CompanyEntity;
import bg.tu.varna.si.server.repository.CarRepository;
import bg.tu.varna.si.server.repository.CompanyRepository;

@Service
public class CarService extends BaseService {

	@Autowired
	private CarRepository carRepository;
	@Autowired
	private CompanyRepository companyRepository;

	public Optional<CarList> getAllCars(long id) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

		if (companyEntity.isPresent()) {
			CarList carsList = new CarList();

			List<CarEntity> carEntities = companyEntity.get().getCars();
			List<Car> cars = new LinkedList<Car>();
			for (CarEntity entity : carEntities) {
				cars.add(fromEntity(entity));
			}

			carsList.setCars(cars);

			return Optional.of(carsList);
		}

		return Optional.empty();
	}

	public Optional<Car> createCar(long id, Car car) {

		Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

		CarEntity entity = carRepository.findByRegNumber(car.getRegNumber());
		if (companyEntity.isPresent() && entity == null) {
			CarEntity carEntity = new CarEntity();
			carEntity.setCompanyId(car.getCompanyId());
			carEntity.setRegNumber(car.getRegNumber());
			carEntity.setBrand(car.getBrand());
			carEntity.setCategory(car.getCategory());
			carEntity.setForSmokers(car.getForSmokers());
			carEntity.setImage(car.getImage());
			carEntity.setType(car.getType());
			carEntity.setPriceForDay(car.getPriceForDay());
			carEntity.setKilometrage(car.getKilometrage());
			carEntity.setAvailable(car.isAvailable());
			carRepository.save(carEntity);

			CompanyEntity company = companyEntity.get();
			List<CarEntity> cars = company.getCars();
			if (cars == null) {
				cars = new LinkedList<CarEntity>();
			}

			cars.add(carEntity);
			companyRepository.save(company);

			Car returnCar = fromEntity(carEntity);
			return Optional.of(returnCar);
		}

		return Optional.empty();
	}

	public Optional<Car> getByID(long companyId, long carId) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(companyId);
		Optional<CarEntity> entity = carRepository.findById(carId);

		if (!entity.isPresent() || !companyEntity.isPresent()) {
			return Optional.empty();
		}
		CarEntity carEntity = entity.get();
		return Optional.of(fromEntity(carEntity));
	}

	public Optional<Car> updateCar(long idCompany, long idCar, Car car) {
		Optional<CompanyEntity> companyEntity = companyRepository.findById(idCompany);
		Optional<CarEntity> carEntity = carRepository.findById(idCar);

		if (companyEntity.isPresent() && carEntity.isPresent()) {

			carEntity.get().setRegNumber(car.getRegNumber());
			carEntity.get().setBrand(car.getBrand());
			carEntity.get().setCategory(car.getCategory());
			carEntity.get().setForSmokers(car.getForSmokers());
			carEntity.get().setImage(car.getImage());
			carEntity.get().setKilometrage(car.getKilometrage());
			carEntity.get().setPriceForDay(car.getPriceForDay());
			carEntity.get().setType(car.getType());
			carEntity.get().setAvailable(car.isAvailable());

			carRepository.save(carEntity.get());

			Car carUpdate = fromEntity(carEntity.get());
			return Optional.of(carUpdate);
		}
		return Optional.empty();
	}

	public void deleteCarEntity(Long id) {

		carRepository.deleteById(id);
	}

	public CarList getAllAvailable(long companyId, Boolean available) {
		
		available = true;

		List <CarEntity> cars = carRepository.findByCompanyIdAndAvailable(companyId, available);

			CarList availableCars = new CarList();
			for (CarEntity car : cars) {
				availableCars.getCars().add(fromEntity(car));
		
			}
			return availableCars;
	}

}
