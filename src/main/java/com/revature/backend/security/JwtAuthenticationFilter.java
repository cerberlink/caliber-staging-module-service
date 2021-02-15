package com.revature.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Jay Monari
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    final String uri = request.getRequestURI();
    if (uri.contains("actuator")) {
      logger.debug("Request to actuator endpoint. Ignoring");
      filterChain.doFilter(request, response);
      return;
    }

    final String header = request.getHeader("authorization");
    String token = null;
    Authentication authentication = null;
    if (header != null && header.startsWith("Bearer ")) {
      token = header.replace("Bearer ", "");
      authentication = authenticationManager.authenticate(new UnauthenticatedManager(token));
    }

    if (authentication instanceof AuthenticatedManager) {
      UsernamePasswordAuthenticationToken auth = new
          UsernamePasswordAuthenticationToken(
            authentication.getName(),
            "",
            AuthorityUtils.createAuthorityList("ROLE_USER"));
      auth.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }
}
