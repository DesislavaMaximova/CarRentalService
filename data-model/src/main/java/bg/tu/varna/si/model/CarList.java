package bg.tu.varna.si.model;

import java.util.LinkedList;
import java.util.List;

public class CarList {

	private List<Car> cars = new LinkedList<>();

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

}
