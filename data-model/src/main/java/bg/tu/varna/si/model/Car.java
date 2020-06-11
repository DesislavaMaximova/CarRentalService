package bg.tu.varna.si.model;

public class Car {
	private long carId;
	
	private String regNumber;
	
	private String brand;
	
	private  CarType type;
	
	private double priceForDay;
	
	private  Boolean forSmokers;
	
	private  CarCategory category;
	
	private  String image;
	
	private double kilometrage;
	
	private boolean available;
	
	private long companyId;
	
	

	public Car() {
	
	}



	public long getCarId() {
		return carId;
	}



	public void setCarId(long carId) {
		this.carId = carId;
	}



	public String getRegNumber() {
		return regNumber;
	}



	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public CarType getType() {
		return type;
	}



	public void setType(CarType type) {
		this.type = type;
	}



	public double getPriceForDay() {
		return priceForDay;
	}



	public void setPriceForDay(double priceForDay) {
		this.priceForDay = priceForDay;
	}



	public Boolean getForSmokers() {
		return forSmokers;
	}



	public void setForSmokers(Boolean forSmokers) {
		this.forSmokers = forSmokers;
	}



	public CarCategory getCategory() {
		return category;
	}



	public void setCategory(CarCategory category) {
		this.category = category;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public double getKilometrage() {
		return kilometrage;
	}



	public void setKilometrage(double kilometrage) {
		this.kilometrage = kilometrage;
	}



	public boolean isAvailable() {
		return available;
	}



	public void setAvailable(boolean available) {
		this.available = available;
	}



	public long getCompanyId() {
		return companyId;
	}



	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}



	@Override
	public String toString() {
	 return regNumber;
	}






	

	
	
	
}
