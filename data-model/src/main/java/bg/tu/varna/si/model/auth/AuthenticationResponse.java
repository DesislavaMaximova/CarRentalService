package bg.tu.varna.si.model.auth;

public class AuthenticationResponse {
	
	private final String jwt;
	private String username;
	private String role;
	private long companyId;

	public AuthenticationResponse(String jwt, String username, String role, long companyId) {
		this.jwt = jwt;
		this.username = username;
		this.role = role;
		this.companyId = companyId;
	}

	public String getJwt() {
		return jwt;
	}
	public String getUsername() {
		return username;
	}
	public String getRole() {
		return role;
	}
	public long getCompanyId() {
		return companyId;
	}
}
