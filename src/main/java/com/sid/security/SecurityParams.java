package com.sid.security;

import java.util.Date;

public interface SecurityParams {

	public static final String JWT_HEADER_NAME  = "Authorization";
	public static final String HEADER_PREFIX    = "Bearer ";
	public static final String SECRET           = "seiidouxx@dev.world.java";
	public static final String CLAIM_ARRAY_NAME = "roles";
	public static final Date   ISSUED_AT        = new Date(System.currentTimeMillis());
	public static final Date   EXPIRE_AT        = new Date(System.currentTimeMillis() + 24 * 3600 * 1000);

}
