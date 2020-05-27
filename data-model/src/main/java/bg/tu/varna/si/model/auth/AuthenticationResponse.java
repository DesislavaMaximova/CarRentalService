package bg.tu.varna.si.model.auth;

public class AuthenticationResponse {
	
	private final String jwt;
	private String username;
	private String role;

	public AuthenticationResponse(String jwt, String username, String role) {
		this.jwt = jwt;
		this.username = username;
		this.role = role;
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
	
}
