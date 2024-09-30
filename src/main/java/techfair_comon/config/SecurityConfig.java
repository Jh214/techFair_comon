package techfair_comon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import techfair_comon.config.jwt.JwtAuthenticationFilter;
import techfair_comon.config.jwt.JwtAuthorizationFilter;
import techfair_comon.user.repository.UserRepository;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthenticationConfiguration authenticationConfiguration;

    private UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository, AuthenticationConfiguration authenticationConfiguration) {
        this.userRepository = userRepository;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        
        config.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors((cors) -> 
            cors.configurationSource(configurationSource())
        );
        http.csrf(csrf -> csrf.disable());

        http.formLogin(form -> form.disable());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()  // Publicly accessible endpoints
            .requestMatchers(new AntPathRequestMatcher("/api/user/**")).permitAll()  // Publicly accessible endpoints
            .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()  // Publicly accessible endpoints
            .anyRequest().authenticated()  // All other endpoints require authentication
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(
            new JwtAuthenticationFilter(authenticationManager()), 
            UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(
            new JwtAuthorizationFilter(userRepository), 
            UsernamePasswordAuthenticationFilter.class    
        );

        return http.build();

    }

}

