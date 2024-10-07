package techfair_comon.security.provider;

import static techfair_comon.security.config.JwtConfig.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import techfair_comon.entity.User;
import techfair_comon.user.enums.Grade;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements Serializable {

    private final SecretKey SECRET_KEY;
    private final long EXPIRATION_TIME;

    private SecretKey getSECRET_KEY() {
        return SECRET_KEY;
    }

    private long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    /**
     * Jwt 생성
     */
    public String generateToken(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().label()));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + getExpirationTime());
        return Jwts.builder()
                .setSubject(user.getUserNo().toString())
                .claim("authorities", roles.stream().map(GrantedAuthority::getAuthority).toList())
                .claim("nickname", user.getUserName())  // 이 부분을 추가하여 nickname 클레임을 설정합니다
                .setIssuer("www.tripcoach.net") // 발급처
                .setIssuedAt(now) // 발급 일자
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, getSECRET_KEY())
                .compact();
    }

    public String loginGenerateToken(Long idx, String nickname, Grade grade) {
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(grade.label()));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + getExpirationTime());
        return Jwts.builder()
                .setSubject(idx.toString())
                .claim("authorities", roles.stream().map(GrantedAuthority::getAuthority).toList())
                .claim("nickname", nickname)  // 이 부분을 추가하여 nickname 클레임을 설정합니다
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, getSECRET_KEY())
                .compact();
    }

    /**
     * JWT 회원 정보 추출.
     */
    public Claims getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSECRET_KEY())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @NotNull
    public String getClaims(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String jwtToken = requestTokenHeader.substring(7);
        Claims claims = getUsernameFromToken(jwtToken);
        claims.getSubject();
        return (String) ((ArrayList<?>) claims.get("authorities")).get(0);
    }
}

