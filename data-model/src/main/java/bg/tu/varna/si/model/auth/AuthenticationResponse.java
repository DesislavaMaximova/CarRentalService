package bg.tu.varna.si.model.auth;

public class AuthenticationResponse {
	
	private final String jwt;
	private String username;
	private String role;
	private long companyId;
	private long userId;

	public AuthenticationResponse(String jwt, String username, String role, long companyId, long userId) {
		this.jwt = jwt;
		this.username = username;
		this.role = role;
		this.companyId = companyId;
		this.userId = userId;
		
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
	public long getUserId() {
		return userId;
	}
}

