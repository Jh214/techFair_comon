package techfair_comon.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.user.dto.LoginDTO; // LoginDto 임포트
import techfair_comon.user.dto.SignupDTO; // SignupDto 임포트
import techfair_comon.user.service.UserService; // UserService 임포트


@RestController
@RequestMapping("/api/user") // API 경로 설정


public class UserController {

    //@Autowired
    private final UserService userService; // UserService 주입
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup") // 회원가입 엔드포인트
    public ResponseDto<Void> signup(@RequestBody SignupDTO signupDto) {
        return userService.signup(signupDto); // UserService의 signup 메소드 호출
    }

    @PostMapping("/login") // 로그인 엔드포인트
    public ResponseDto<Void> login(@RequestBody LoginDTO loginDto) {
        return userService.login(loginDto); // UserService의 login 메소드 호출
    }
}