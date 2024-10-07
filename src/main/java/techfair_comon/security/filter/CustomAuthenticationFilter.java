package techfair_comon.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import techfair_comon.security.token.CustomAuthenticationToken;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파서 객체

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // Content-Type이 JSON인지 확인
        if (!request.getContentType().equalsIgnoreCase("application/json")) {
            throw new AuthenticationException("Content-Type must be application/json") {};
        }

        // 요청 본문에서 JSON 데이터 읽기
        try {
            // Map<String, Object>로 변경
            Map<String, Object> authData = objectMapper.readValue(request.getInputStream(), Map.class);

            // userNo와 userPw를 String으로 변환
            String userNo = String.valueOf(authData.get("userNo"));
            String userPw = String.valueOf(authData.get("userPw"));

            CustomAuthenticationToken customToken = new CustomAuthenticationToken(userNo, userPw);
            return getAuthenticationManager().authenticate(customToken);
        } catch (IOException e) {
            throw new AuthenticationException("Failed to read authentication data") {};
        }
    }
}

//
//
//public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//
//    public CustomAuthenticationFilter() {
//        super(new AntPathRequestMatcher("/login"));
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//
//        String userNo = request.getParameter("userNo");
//        String userPw = request.getParameter("userPw");
//
//        CustomAuthenticationToken customToken = new CustomAuthenticationToken(userNo, userPw);
//
//        return getAuthenticationManager().authenticate(customToken);
//    }
//
//}
//
//
