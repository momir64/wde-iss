package wedoevents.eventplanner.shared.config.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "5057f9d5119a59e16f923bbf716e002910872d17b77fbb848ccfb41b548e39a491eec1a421b0b37be2a52d9813dee4dfd1c134b4ee5f27274c0308e58cc69fdc";
    private static final Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    public static String generateToken(String username, List<String> roles, UUID profileId, UUID userId) {
        return Jwts.builder()
                   .setSubject(username)
                   .claim("profileId", profileId)
                   .claim("roles", roles)
                   .claim("userId", userId)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key).build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String extractJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    public static UUID extractProfileId(HttpServletRequest request) {
        String token = extractJwt(request);
        if (token == null) throw new IllegalArgumentException();
        return UUID.fromString(Jwts.parserBuilder()
                                   .setSigningKey(key).build()
                                   .parseClaimsJws(token)
                                   .getBody()
                                   .get("profileId", String.class));
    }

    public static UUID extractUserId(HttpServletRequest request) {
        String token = extractJwt(request);
        if (token == null) throw new IllegalArgumentException();
        return UUID.fromString(Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", String.class));
    }
}