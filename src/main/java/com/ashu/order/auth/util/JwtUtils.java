package com.ashu.order.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ashu.order.auth.config.JwtConfigProperties;
import com.ashu.order.auth.model.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that provides methods for handling jwt token.
 *
 * @author ashutoshsahoo
 *
 */
@Component
@Slf4j
public class JwtUtils {

	private final JwtConfigProperties jwtConfigProperties;

	public JwtUtils(JwtConfigProperties jwtConfigProperties) {
		super();
		this.jwtConfigProperties = jwtConfigProperties;
	}

	/*
	 * Retrieve username from jwt token.
	 */
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtConfigProperties.getSecret().getBytes()).build()
				.parseClaimsJws(token).getBody().getSubject();
	}

	/*
	 * Create token.
	 */
	public String generateToken(Authentication authentication) {

		SecretKey jwtSecretKey = Keys.hmacShaKeyFor(jwtConfigProperties.getSecret().getBytes());
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setHeaderParam("type", jwtConfigProperties.getType()).setIssuer(jwtConfigProperties.getIssuer())
				.setAudience(jwtConfigProperties.getAudience())
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfigProperties.getTokenValidity()))
				.signWith(jwtSecretKey, SignatureAlgorithm.HS512).compact();
	}

	/*
	 * Validate token.
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtConfigProperties.getSecret().getBytes()).build()
					.parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

}
