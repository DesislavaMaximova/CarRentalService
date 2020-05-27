package bg.tu.varna.si.server.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bg.tu.varna.si.server.db.entity.UserEntity;
import bg.tu.varna.si.server.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(userName);

		if (userEntity == null) {
			throw new UsernameNotFoundException("Username [" + userName + "] not found");
		}

		return new User(userEntity.getUsername(), userEntity.getPassword(), getAuthorities(userEntity));
	}

	private Collection<GrantedAuthority> getAuthorities(UserEntity user) {
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		String role = user.getRole().toString();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

}
