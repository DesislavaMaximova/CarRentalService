package bg.tu.varna.si.server.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import bg.tu.varna.si.model.CarCategory;
import bg.tu.varna.si.model.CarType;

@Entity
@Table(name = "Car")
public class CarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String regNumber;

	private String brand;

	private CarType type;

	private double priceForDay;

	private Boolean forSmokers;

	private CarCategory category;

	private String image;
	
	private double kilometrage;
	
	private boolean available;
	
	private long companyId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		StringBuilder builder = new StringBuilder();
		builder.append("CarEntity [id=");
		builder.append(id);
		builder.append(", regNumber=");
		builder.append(regNumber);
		builder.append(", brand=");
		builder.append(brand);
		builder.append(", type=");
		builder.append(type);
		builder.append(", priceForDay=");
		builder.append(priceForDay);
		builder.append(", forSmokers=");
		builder.append(forSmokers);
		builder.append(", category=");
		builder.append(category);
		builder.append(", image=");
		builder.append(image);
		builder.append(", kilometrage=");
		builder.append(kilometrage);
		builder.append(", available=");
		builder.append(available);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}
	

}
