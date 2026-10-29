package com.startup.controllers;

import com.startup.domain.user.AuthenticationDTO;
import com.startup.domain.user.LoginResponseDTO;
import com.startup.domain.user.RegisterDTO;
import com.startup.domain.user.SessionResponseDTO;
import com.startup.domain.user.User;
import com.startup.domain.user.UserRole;
import com.startup.infra.exception.ConflictException;
import com.startup.infra.security.TokenService;
import com.startup.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository users;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
    var credentials = new UsernamePasswordAuthenticationToken(data.email(), data.password());
    var authentication = authenticationManager.authenticate(credentials);
    var user = (User) authentication.getPrincipal();
    var token = tokenService.generateToken(user);
    return ResponseEntity.ok(new LoginResponseDTO(token, user.getId()));
  }

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
    if (users.findByEmail(data.email()).isPresent()) {
      throw new ConflictException("E-mail já cadastrado");
    }
    users.save(new User(data.email(), passwordEncoder.encode(data.password()), UserRole.USER));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/session")
  public ResponseEntity<SessionResponseDTO> session(Authentication authentication) {
    var user = (User) authentication.getPrincipal();
    return ResponseEntity.ok(new SessionResponseDTO(user.getId()));
  }
}
