package bg.tu.varna.si.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import bg.tu.varna.si.server.service.DatabaseUserDetailsService;
import bg.tu.varna.si.server.service.JwtService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final String AUTH = "Authorization";

	private static final String BEARER = "Bearer ";

	@Autowired
	private DatabaseUserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(AUTH);

		String userName = null;
		String token = null;

		if (authorizationHeader != null
				&& authorizationHeader.startsWith(BEARER)) {
			token = authorizationHeader.substring(BEARER.length());
			userName = jwtService.extractUsername(token);
		}

		if (userName != null
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService
					.loadUserByUsername(userName);

			if (jwtService.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken
						.setDetails(new WebAuthenticationDetailsSource()
								.buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(
						authenticationToken);
			} else {
				System.out.println("Token is invalid!");
			}
		}

		filterChain.doFilter(request, response);

	}

}
