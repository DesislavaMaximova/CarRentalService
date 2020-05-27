package bg.tu.varna.si.server.controller.auth;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.tu.varna.si.model.auth.AuthenticationRequest;
import bg.tu.varna.si.model.auth.AuthenticationResponse;
import bg.tu.varna.si.server.service.DatabaseUserDetailsService;
import bg.tu.varna.si.server.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private DatabaseUserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest credentials)
			throws Exception {
		
		LOGGER.debug("Authenticating user: {}", credentials.getUsername());

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

		UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
		String jwt = jwtService.generateToken(userDetails);
		String username = credentials.getUsername();
		List<GrantedAuthority> list = new LinkedList<>(userDetails.getAuthorities());
		String role = list.get(0).getAuthority();
		return ResponseEntity.ok(new AuthenticationResponse(jwt, username, role));
	}

}