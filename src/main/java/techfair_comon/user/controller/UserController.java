package techfair_comon.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto;
import techfair_comon.user.dto.LoginDTO;
import techfair_comon.user.dto.SignupDTO;
import techfair_comon.user.service.UserService;
import techfair_comon.user.service.CoolSmsService;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final CoolSmsService coolSmsService;

    @PostMapping("/signup") // 회원가입 엔드포인트
    public ResponseDto<Void> signup(@RequestBody SignupDTO signupDto) {
        // certificationCode를 SignupDTO에서 받음
        return userService.signup(signupDto);
    }

    @PostMapping("/login") // 로그인 엔드포인트
    public ResponseDto<Void> login(@RequestBody LoginDTO loginDto) {
        return userService.login(loginDto);
    }

    // 새로운 GET 엔드포인트 - /hello 추가
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    // 인증 코드 전송 엔드포인트
    @PostMapping("/sms/send-code")
    public String sendCertificationCode(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "전화번호가 필요합니다.";
        }

        return coolSmsService.sendCertificationCode(phoneNumber);
    }
}
