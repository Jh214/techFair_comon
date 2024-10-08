package techfair_comon.security.filter;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import techfair_comon.security.enums.SecurityCode;
import techfair_comon.security.exception.AuthFailException;
import techfair_comon.security.provider.JwtTokenProvider;
import techfair_comon.security.service.CustomUserDetailsServiceImpl;
import techfair_comon.user.enums.Grade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static techfair_comon.security.config.JwtConfig.AUTHORIZATION_HEADER;
import static techfair_comon.security.config.JwtConfig.BEARER_PREFIX;
import static techfair_comon.security.enums.SecurityCode.*;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String jwtToken;
        String username = null;
        Claims claims = null;

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
                jwtToken = requestTokenHeader.substring(BEARER_PREFIX.length());
                claims = jwtTokenProvider.getUsernameFromToken(jwtToken);
                String nickname = (String) claims.get("nickname");
                username = claims.getSubject();
                log.info("인덱스 = {} 닉네임 = {} RequestURL= {}", username, nickname, request.getRequestURL());

//                checkRequestIp(request, claims);
            } else {
                log.info("토큰 없이 요청들어온 url= {} ,요청자 ip url= {}  ", request.getRequestURL(), request.getHeader("x-forwarded-for"));
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUserIfValidToken(claims, username, request);
            }
        } catch (ExpiredJwtException e) {
            handleJwtException(EXPIRED_JWT_EXCEPTION, request);
        } catch (MalformedJwtException e) {
            handleJwtException(MALFORMED_JWT_EXCEPTION, request);
        } catch (UnsupportedJwtException e) {
            handleJwtException(UNSUPPORTED_JWT_EXCEPTION, request);
        } catch (SignatureException e) {
            handleJwtException(SIGNATURE_EXCEPTION, request);
        } catch (IllegalArgumentException e) {
            handleJwtException(ILLEGAL_ARGUMENT_EXCEPTION, request);
        } catch (NullPointerException e) {
            handleJwtException(NULL_POINT_EXCEPTION, request);
        } catch (AuthFailException e) {
            log.warn(IP_NOT_MATCHING_EXCEPTION.label());
            handleJwtException(IP_NOT_MATCHING_EXCEPTION, request);
        }
        chain.doFilter(request, response);
    }


    private void authenticateUserIfValidToken(Claims claims, String username, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(claims, username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private UserDetails getUserDetails(Claims claims, String username) {
        ArrayList<GrantedAuthority> authorities = (ArrayList<GrantedAuthority>) claims.get("authorities");
        Grade grade = Grade.valueOf(String.valueOf(authorities.get(0)));
        String nickname = (String) claims.get("userName");

        return userDetailsService.loadUserByUsername(Long.valueOf(username), nickname, grade);
    }

    private void handleJwtException(SecurityCode jwtException, HttpServletRequest request) {
        request.setAttribute(jwtException.label(), jwtException);
    }
}
