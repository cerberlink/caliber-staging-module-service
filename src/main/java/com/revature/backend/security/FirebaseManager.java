package com.revature.backend.security;

import com.google.firebase.auth.FirebaseToken;

/**
 * @author Jay Monari
 */
public class FirebaseManager extends AuthenticatedManager {
  private static final long serialVersionUID = 5679872493310689712L;

  public FirebaseManager(FirebaseToken token) {
    super(token.getClaims());
  }
}
