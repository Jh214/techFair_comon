package techfair_comon.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import techfair_comon.ResponseDto;
import techfair_comon.security.provider.JwtTokenProvider;
import techfair_comon.security.service.UserContext;
import techfair_comon.user.enums.Grade;

import java.io.IOException;
import java.io.PrintWriter;

import static techfair_comon.ResponseDto.setSuccessData;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        Long idx = ((UserContext) authentication.getPrincipal()).getUserNo();
        String nickname = ((UserContext) authentication.getPrincipal()).getNickname();
        Grade grade = ((UserContext) authentication.getPrincipal()).getGrade();

        String token = jwtTokenProvider.loginGenerateToken(idx, nickname, grade);

        ResponseDto<String> success = setSuccessData("로그인 성공", token);

        // Convert the DTO to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(success);

        // Set the content type of the response to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON string to the response body
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}