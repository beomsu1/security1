package bs.org.java.security.infrastructure.config;

import bs.org.java.security.infrastructure.security.JwtAuthenticationFilter;
import bs.org.java.security.infrastructure.security.UserDetailsServiceImpl;
import bs.org.java.security.util.JwtTokenProviderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProviderUtil jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/v3/api-docs", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/signup").permitAll()
                .requestMatchers("/sign").permitAll()
                .anyRequest().authenticated()
        );

        // JwtAuthenticationFilter 먼저 실행
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
