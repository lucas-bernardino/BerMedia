package com.movie.server.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.movie.server.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final String TOKEN_SECRET = "secret";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException jwtCreationException) {
            throw new RuntimeException("Error while generating token", jwtCreationException);
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}
