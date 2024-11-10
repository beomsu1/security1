package bs.org.java.security;

import bs.org.java.security.util.JwtTokenProviderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenProviderUtilTest {

    private JwtTokenProviderUtil jwtTokenProvider;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProviderUtil();
        jwtTokenProvider.secretKey = secretKey;
        jwtTokenProvider.accessTokenExpiration = accessTokenExpiration;
        jwtTokenProvider.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Test
    @DisplayName("엑세스 토큰 생성 테스트")
    void generateAccessTokenTest() {
        String username = "beomsu";
        List<String> roles = List.of("ROLE_USER");

        String accessToken = jwtTokenProvider.generateAccessToken(username, roles);

        assertNotNull(accessToken, "엑세스 토큰이 일치하지 않습니다.");
    }

    @Test
    @DisplayName("리프레시 토큰 생성 테스트")
    void generateRefreshTokenTest() {
        String username = "beomsu";

        String refreshToken = jwtTokenProvider.generateRefreshToken(username);

        assertNotNull(refreshToken, "리프레시 토큰이 일치하지 않습니다.");
    }

    @Test
    @DisplayName("엑세스 토큰 검증 테스트")
    void validateAccessTokenTest() {
        String username = "beomsu";
        List<String> roles = List.of("ROLE_USER");
        String accessToken = jwtTokenProvider.generateAccessToken(username, roles);

        boolean isValid = jwtTokenProvider.validateToken(accessToken);

        assertTrue(isValid, "엑세스 토큰 검증 완료되었습니다.");
    }

    @Test
    @DisplayName("리프레시 토큰 검증 테스트")
    void validateRefreshTokenTest() {
        String username = "beomsu";
        String refreshToken = jwtTokenProvider.generateRefreshToken(username);

        boolean isValid = jwtTokenProvider.validateToken(refreshToken);

        assertTrue(isValid, "리프레시 토큰 검증 완료되었습니다.");
    }
}

