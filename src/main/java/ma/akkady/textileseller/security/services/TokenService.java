package ma.akkady.textileseller.security.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {
    private final Duration accessTokenExpiry;
    private final Duration refreshTokenExpiry;
    private final Algorithm algorithm;

    public TokenService(@Value("{jwt.secret}") String privateKey, @Value("${jwt.access.exp}") long accessExpiry, @Value("${jwt.refresh.exp}") long refreshExpiry) {
        this.algorithm = Algorithm.HMAC256(privateKey);
        this.accessTokenExpiry = Duration.ofMinutes(accessExpiry);
        this.refreshTokenExpiry = Duration.ofMinutes(refreshExpiry);
    }

    public String generateAccessToken(String subject, List<String> roles, String issuer) {
        Instant now = Instant.now();
        // Set expiration time
        Date expirationTime = Date.from(now.plus(accessTokenExpiry));
        // Build JWT with subject and expiration time
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expirationTime)
                .withIssuer(issuer)
                .withIssuedAt(Date.from(now))
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public String generateRefreshToken(String subject, String issuer) {
        Instant now = Instant.now();
        // Set expiration time
        Date expirationTime = Date.from(now.plus(refreshTokenExpiry));
        // Build JWT with subject and expiration time
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expirationTime)
                .withIssuer(issuer)
                .withIssuedAt(Date.from(now))
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

}
