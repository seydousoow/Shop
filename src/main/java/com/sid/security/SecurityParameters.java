package com.sid.security;

class SecurityParameters {
    static final String HEADER = "Authorization";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String CLAIMS_NAME = "roles";
    static final String SECRET = "Jwt@$&cr&t4As2$hop!";
    static final long EXPIRATION_TIME_MILLIS = 10 * 24 * 60 * 60 * 1000;
}
