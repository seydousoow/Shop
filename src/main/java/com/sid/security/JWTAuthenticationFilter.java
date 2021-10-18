package com.sid.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.entities.AppUser;
import com.sid.dto.CustomUserDetails;
import com.sid.exception.RestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.sid.security.SecurityParameters.*;
import static io.jsonwebtoken.io.Encoders.BASE64;
import static java.nio.charset.StandardCharsets.UTF_8;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser user;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        } catch (IOException e) {
            throw new RestException(e);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        val user = (CustomUserDetails) authResult.getPrincipal();
        val jwt = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(UTF_8)), SignatureAlgorithm.HS512)
                .setId(user.getId())
                .setSubject(user.getUsername())
                .setIssuer("Beauty Zahra")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .claim(CLAIMS_NAME, user.getAuthorities())
                .compact();

        response.addHeader(HEADER, jwt);
    }
}
