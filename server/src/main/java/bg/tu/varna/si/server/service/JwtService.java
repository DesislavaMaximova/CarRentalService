package bg.tu.varna.si.server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String jwtSecretKey;
	
	@Value("${jwt.token.validity}")
	private int jwtTokenValidity;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new LinkedHashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return userDetails.getUsername().equals(userName) && !isTokedExpired(token);
	}


	private String createToken(Map<String, Object> claims, String subject) {
		long now = System.currentTimeMillis();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + 1000 * 60 * jwtTokenValidity)) 
				.signWith(SignatureAlgorithm.HS512, jwtSecretKey)
				.compact();
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokedExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
