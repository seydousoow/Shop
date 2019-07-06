package com.sid.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.entities.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser user;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        User springUser = (User) authResult.getPrincipal();
        String encodedSecret = new String (Base64.getEncoder().encode(SecurityParameters.SECRET.getBytes()));

        String jwt = Jwts.builder()
                .setSubject(springUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityParameters.EXPIRATION_TIME_MILLIS))
                .claim(SecurityParameters.CLAIMS_NAME, springUser.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();

        response.addHeader(SecurityParameters.HEADER, jwt);
    }
}
