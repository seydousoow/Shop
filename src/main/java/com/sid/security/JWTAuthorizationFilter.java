package com.sid.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		/*
		 * Domain authorized to send request
		 * Headers that are allowed to be sent to this domain
		 * Allows other methods (PUT, Delete)
		 */
		setHeaders(response);

		if (request.getMethod().equals("OPTIONS"))
			response.setStatus(HttpServletResponse.SC_OK);
		else 
			JWTFilter(request, response, filterChain);

	}
		
	private void JWTFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		/*
		 * Get the token from the response's header
		 * and check if the token is not null and if it start with the correct token's prefix
		 */
		String token = request.getHeader(SecurityParameters.HEADER);
		if (token == null || !token.startsWith(SecurityParameters.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		String encodedString = new String(Base64.getEncoder().encode(SecurityParameters.SECRET.getBytes()));
		Claims claims = Jwts.parser()
				.setSigningKey(encodedString)
				.parseClaimsJws(token.substring(SecurityParameters.TOKEN_PREFIX.length()))
				.getBody();

		String username = claims.getSubject();
		ArrayList<Map<String, String>> roles;
		roles = (ArrayList<Map<String, String>>) claims.get(SecurityParameters.CLAIMS_NAME);
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.get("authority"))));

		/*
		 * create a spring user with the previous credentials
		 * set the user as the one that has authenticated in this context
		 * then do the filter
		 */
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(user);
		filterChain.doFilter(request, response);
	}

	private void setHeaders(@org.jetbrains.annotations.NotNull HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, "
				+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");

		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
	}
}
