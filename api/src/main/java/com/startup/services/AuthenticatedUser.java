package com.startup.services;

import com.startup.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticatedUser {

  public User get() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida");
    }
    return user;
  }
}
