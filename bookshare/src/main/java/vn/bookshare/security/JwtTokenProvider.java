package vn.bookshare.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.bookshare.exception.CustomExpiredJwtException;

@Component
public class JwtTokenProvider {

    @Value("${jwt.expiration}")
    private long expiration;
    private final SecretKey secretKey;

    public JwtTokenProvider(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer("bookshare.vn")
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            Claims claims = jws.getPayload();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new CustomExpiredJwtException("JWT expired" + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Jwt is not correct format" + e.getMessage());
        } catch (JwtException e) {
            throw new JwtException("Jwt is not correct format" + e.getMessage());
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            throw new CustomExpiredJwtException("JWT expired" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Token not support" + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Jwt is not correct format" + e.getMessage());
        } catch (SecurityException e) {
            throw new SecurityException("User information is not correct" + e.getMessage());
        } catch (JwtException e) {
            throw new JwtException("Jwt is not correct format" + e.getMessage());
        }
    }
}
