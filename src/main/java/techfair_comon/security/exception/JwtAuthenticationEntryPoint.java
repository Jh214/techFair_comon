package techfair_comon.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import techfair_comon.ResponseDto;
import techfair_comon.security.enums.SecurityCode;

import java.io.IOException;
import java.io.PrintWriter;

import static techfair_comon.ResponseDto.setAuthFailedData;
import static techfair_comon.security.enums.SecurityCode.*;
import static techfair_comon.user.enums.Grade.ROLE_ANONYMOUS;


@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        SecurityCode jwtException = (SecurityCode) request.getAttribute(JWT_EXCEPTION.label());
        String errorMessage;
        int errorCode;

        if (jwtException != null) {
            errorCode = switch (jwtException) {
                case EXPIRED_JWT_EXCEPTION -> {
                    errorMessage = EXPIRED_JWT_EXCEPTION.message();
                    yield 401;
                }
                case MALFORMED_JWT_EXCEPTION -> {
                    errorMessage = MALFORMED_JWT_EXCEPTION.message();
                    yield 401;
                }
                case UNSUPPORTED_JWT_EXCEPTION -> {
                    errorMessage = UNSUPPORTED_JWT_EXCEPTION.message();
                    yield 401;
                }
                case SIGNATURE_EXCEPTION -> {
                    errorMessage = SIGNATURE_EXCEPTION.message();
                    yield 401;
                }
                case IP_NOT_MATCHING_EXCEPTION -> {
                    errorMessage = IP_NOT_MATCHING_EXCEPTION.message();
                    yield 401;
                }
                case ILLEGAL_ARGUMENT_EXCEPTION -> {
                    errorMessage = ILLEGAL_ARGUMENT_EXCEPTION.message();
                    yield 400;
                }
                case NULL_POINT_EXCEPTION -> {
                    errorMessage = NULL_POINT_EXCEPTION.message();
                    yield 400;
                }
                default -> {
                    errorMessage = JWT_EXCEPTION.message(); // 기본적으로 설정할 메시지
                    yield 500;
                }
            };
        } else {
            errorMessage = ROLE_ANONYMOUS.label();
            errorCode = 403;
        }

        ResponseDto<?> error = setAuthFailedData(errorMessage, String.valueOf(errorCode), jwtException);

        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(error);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode);

        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}