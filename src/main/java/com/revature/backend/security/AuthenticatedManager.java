package com.revature.backend.security;

import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @author Jay Monari
 */
public class AuthenticatedManager extends
      AbstractAuthenticationToken {
  private static final long serialVersionUID = 6559911895414362557L;
  private final Map<String, Object> claims;

  public AuthenticatedManager(Map<String, Object> claims) {
    super(AuthorityUtils.NO_AUTHORITIES);
    this.claims = claims;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return claims.getOrDefault("email", "");
  }

  @Override
  public String getName() {
    return (String) claims.getOrDefault("email", "");
  }
}
