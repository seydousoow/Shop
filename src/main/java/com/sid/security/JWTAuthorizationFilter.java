package com.sid.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		/*
		 * domain authorized to send request
		 */
		response.addHeader("Access-Control-Allow-Origin", "*");

		/*
		 * headers that are allowed to be sent to this domain
		 */
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, "
		            + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");

		response.addHeader("Access-Control-Expose-Headers",
		            "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
		/*
		 * Allows other methods (PUT, Delete)
		 */
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else 
			JWTFilter(request, response, filterChain);

	}
		
	private void JWTFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		/*
		 * Get the token from the response's header
		 * and check if the token is not null and if it start with the correct token's prefix
		 */
		String token = request.getHeader(SecurityParams.JWT_HEADER_NAME);
		if (token.isEmpty() || token == null || !token.startsWith(SecurityParams.HEADER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		/*
		 * if the token matches the requisitions build a verifier with the algorith set in during the token's creation,
		 * decoded it and get the user and his roles
		 */
		JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SecurityParams.SECRET)).build();
		
		token = token.substring(SecurityParams.HEADER_PREFIX.length());
		DecodedJWT decoded = verifier.verify(token);
		
		String username = decoded.getSubject();
		List<String> roles = decoded.getClaims().get(SecurityParams.CLAIM_ARRAY_NAME).asList(String.class);
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(rn -> {
			authorities.add(new SimpleGrantedAuthority(rn));
		});

		/*
		 * create a spring user with the previous credentials
		 * set the user as the one that has authenticated in this context
		 * then do the filter
		 */
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null,
		            authorities);

		SecurityContextHolder.getContext().setAuthentication(user);

		filterChain.doFilter(request, response);
	}

}
