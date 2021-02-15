package com.revature.backend.config;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.ErrorCode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.revature.backend.security.FirebaseAuthenticationManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * This configuration class is used to start the Firebase API Admin SDK in order
 * for this configuration to work you must set in the application.properties
 * spring.profiles.active=firebase or else the security configuration will not
 * find a bean for the AuthenticationManager interface. And since it is an
 * interface we must make a concrete class for the bean to be initialized.
 * You must also set your environment variable GOOGLE_APPLICATION_CREDENTIALS
 * to the path of your serviceaccount.json with your private key. Do not allow
 * that file into version control!
 * @author Jay Monari
 */
@Configuration
@Profile("firebase")
public class FirebaseConfig {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private FirebaseAuth firebaseAuth;

  /**
   * Here we instantiate our {@link FirebaseApp} and {@link FirebaseAuth}. If
   * the {@link FirebaseApp} is already instantiated we set the Auth to the 
   * current DEFAULT {@link FirebaseApp} else the server has started up for the 
   * first time and we need to initialize it.
   * @throws FirebaseException
   */
  public FirebaseConfig() throws FirebaseException {
    System.out.println("\n This should not be null: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS") + "\n"); //TODO if null check STARTUP.md
    try (FileInputStream fileInputStream = new FileInputStream(
          System.getenv("GOOGLE_APPLICATION_CREDENTIALS"))) {
      try {
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
      } catch (IllegalStateException e) {
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(fileInputStream))
            .build();
        this.firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.initializeApp(options));
      }
      logger.info("Successfully initialized Firebase and FirebaseAuth!");
    } catch (IOException e) {
      logger.error("Failed to instantiate Firebase", e);
      throw new FirebaseException(
          ErrorCode.UNAVAILABLE, "Failed to instantiate Firebase", e);
    }
  }

  @Bean
  public AuthenticationManager firebaseAuthenticationManager() {
    return new FirebaseAuthenticationManager(this.firebaseAuth);
  }
}
