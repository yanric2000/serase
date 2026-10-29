package com.startup.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.startup.domain.user.User;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private static final String ISSUER = "startup";
  private final Algorithm algorithm;

  public TokenService(@Value("${api.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  public String generateToken(User user) {
    try {
      return JWT.create()
          .withIssuer(ISSUER)
          .withSubject(user.getEmail())
          .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new IllegalStateException("Não foi possível gerar o token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      return null;
    }
  }
}
