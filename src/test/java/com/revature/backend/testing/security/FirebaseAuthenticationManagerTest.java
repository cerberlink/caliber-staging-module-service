package com.revature.backend.testing.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.revature.backend.security.FirebaseAuthenticationManager;
import com.revature.backend.testing.security.SecurityMockedClasses.MockAuthentication;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

@SpringBootTest
public class FirebaseAuthenticationManagerTest {

  @InjectMocks
  private FirebaseAuthenticationManager fireAuthMan;
  @Mock
  FirebaseAuth mockFireAuth;

  private Authentication mockAuth;
  private String credentials;
  private FirebaseToken token;

  @BeforeEach
  public void before()throws Exception {
    mockAuth = new MockAuthentication();//Implementation of this mock was moved to its own class to reduce length and help with readability
    when(mockFireAuth.verifyIdToken((String)mockAuth.getCredentials())).thenReturn(null);
  }

  @AfterEach
  public void after(){

  }
  @Test
  public void notNullTest(){
    fireAuthMan=new FirebaseAuthenticationManager(mockFireAuth);
    assertThat(fireAuthMan).isNotNull();
  }

  @Test
  public void userIsVerifiedTest() throws Exception{
    fireAuthMan=new FirebaseAuthenticationManager(mockFireAuth);
  }
  
}
