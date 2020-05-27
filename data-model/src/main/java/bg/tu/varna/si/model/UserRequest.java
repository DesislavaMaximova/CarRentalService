package bg.tu.varna.si.model;

import bg.tu.varna.si.model.auth.AuthenticationRequest;

public class UserRequest {
	
	private User user;
	
	private AuthenticationRequest auth;
	
	public UserRequest () {
		
	}
	
	public UserRequest(User user, AuthenticationRequest auth) {
		this.user = user;
		this.auth = auth;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public AuthenticationRequest getAuth() {
		return auth;
	}
	
	public void setAuth(AuthenticationRequest auth) {
		this.auth = auth;
	}

}
