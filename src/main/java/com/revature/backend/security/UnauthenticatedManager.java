package com.revature.backend.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Jay Monari
 */
public class UnauthenticatedManager extends 
      UsernamePasswordAuthenticationToken {
  private static final long serialVersionUID = 8957908813414540861L;

  public UnauthenticatedManager(String bearerToken) {
    super(null, bearerToken);
  }
}
