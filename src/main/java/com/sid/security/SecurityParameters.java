package com.sid.security;

final class SecurityParameters {
    static final String HEADER = "Authorization";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String CLAIMS_NAME = "roles";
    static final String SECRET = "5v8y/B?E(H+ThWmZq4t7wTjWnZr4u7w!z%C*bQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A!z%jXn2r5u8x/A%D*G-KaPdSgVkYpMbQeThWmZq4t7w9z$C&F)J@NcRfUG+KbPeShVmYq3s6v9y$B&E)H@McQf?D(G+KbPdSgVkYp3s6v9y$B&E) H@McQf3s6v9y$B&E(H+Mx/A?D(C*F-JaNdRgUkXn2r5u8F-JaNdRgUkXp2s5v8y/A?D(G++";
    static final long EXPIRATION_TIME_MILLIS = 10 * 24 * 60 * 60 * 1000L;
    private SecurityParameters() {
    }
}
