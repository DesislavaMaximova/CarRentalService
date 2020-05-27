package bg.tu.varna.si.model;

import java.util.LinkedList;
import java.util.List;

public class UserList {

	private List <User> users = new LinkedList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
}
