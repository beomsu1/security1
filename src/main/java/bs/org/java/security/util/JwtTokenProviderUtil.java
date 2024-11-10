package bs.org.java.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProviderUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    public String secretKey;

    @Value("${jwt.access-token-expiration}")
    public long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    public long refreshTokenExpiration;

    // 엑세스 토큰 생성
    public String generateAccessToken(String username, List<String> roles) {
        return createToken(username, roles, accessTokenExpiration);
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String username) {
        return createToken(username, null, refreshTokenExpiration);
    }

    // 공통 토큰 생성 메서드
    private String createToken(String username, List<String> roles, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(username);
        if (roles != null) {
            claims.put("roles", roles);
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
