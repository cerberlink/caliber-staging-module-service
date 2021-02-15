package com.revature.backend.testing.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.revature.backend.security.JwtAuthenticationFilter;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootTest
public class JwtAuthenticationFilterTest {
  @Mock
  private AuthenticationManager mockAuthMan;

  @InjectMocks
  private JwtAuthenticationFilter jwtAuth;

  @BeforeEach
  public void before(){
    

  }

  @AfterEach
  public void after(){

  }

  @Test
  public void notNullTest(){
    jwtAuth=new JwtAuthenticationFilter(mockAuthMan);
    assertThat(jwtAuth).isNotNull();;
  }
  
}
