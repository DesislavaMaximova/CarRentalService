package bg.tu.varna.si.model;

public class Client extends User {
	
	
	private int rating;
	private String telephone;
	private String driversLicense;
	
	public Client(String username, String firstName, String lastName, String email, Role role, int rating, String telephone, String driversLicense) {
		super(Role.CLIENT);
		
		this.telephone = telephone;
		this.driversLicense = driversLicense;
		this.rating = rating;
		
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
