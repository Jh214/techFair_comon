package techfair_comon.security.config;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import techfair_comon.security.exception.JwtAuthenticationEntryPoint;
import techfair_comon.security.filter.CustomAuthenticationFilter;
import techfair_comon.security.filter.JwtAuthenticationFilter;
import techfair_comon.security.handler.CustomAccessDeniedHandler;
import techfair_comon.security.handler.CustomAuthenticationFailureHandler;
import techfair_comon.security.handler.CustomAuthenticationSuccessHandler;
import techfair_comon.security.provider.CustomAuthenticationManager;

import java.util.Collections;

import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    String[] roles = {"MEMBER", "ADMIN", "PLANNER", "GUIDE", "AGENCY", "ETC_SELLER"};
    public static final String[] PERMIT_ALL = {"/login", "/join", "/login/web", "root"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 Disable
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(getCorsConfigurerCustomizer())
                //.authorizeHttpRequests(getAuthorizationManagerRequestMatcherRegistryCustomizer())
                .exceptionHandling(getExceptionHandlingConfigurerCustomizer()) // exception handling 할 때 우리가 만든 클래스를 추가.
                .sessionManagement(getSessionManagementConfigurerCustomizer()) // 시큐리티는 기본적으로 세션을 사용하지만, 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add a filter to validate the tokens with every request
                .build();
    }

    //@NotNull
    //private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> getAuthorizationManagerRequestMatcherRegistryCustomizer() {
      //  return authorize -> authorize
        //        .requestMatchers(PERMIT_ALL).permitAll()
          //      .anyRequest().authenticated();
    //}

    @NotNull
    private Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurerCustomizer() {
        return cors ->
                cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.addAllowedMethod("*"); // Allow all HTTP methods
                    configuration.addAllowedHeader("*"); // Allow all headers

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);

                    String origin = request.getHeader("origin");

                    configuration.setAllowedOrigins(Collections.singletonList(origin));
                    configuration.setAllowCredentials(true);
                    return configuration;
                });
    }

    @NotNull
    private Customizer<SessionManagementConfigurer<HttpSecurity>> getSessionManagementConfigurerCustomizer() {
        return sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @NotNull
    private Customizer<ExceptionHandlingConfigurer<HttpSecurity>> getExceptionHandlingConfigurerCustomizer() {
        return exceptionHandling ->
                exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }


    // 커스텀 인증 필터
    @Bean
    public CustomAuthenticationFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(customAuthenticationManager());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        return filter;
    }

    // 커스텀 인증 매니저
    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

