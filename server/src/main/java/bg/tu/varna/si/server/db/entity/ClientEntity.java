package bg.tu.varna.si.server.db.entity;

import javax.persistence.Entity;

@Entity
public class ClientEntity extends UserEntity {
	
	private int rating;
	
	private String telephone;
	
	private String driversLicense;
	
	public ClientEntity() {
		
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}
	
}
