package techfair_comon.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.user.dto.SignupDTO; // SignupDto 임포트
import techfair_comon.user.service.UserService; // UserService 임포트


@RestController
@RequestMapping("/api/user") // API 경로 설정
public class UserController {

    // @Autowired
    private final UserService userService; // UserService 주입
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup") // 회원가입 엔드포인트
    public ResponseDto<Void> signup(@RequestBody SignupDTO signupDto) {
        return userService.signup(signupDto); // UserService의 signup 메소드 호출
    }

//    @PostMapping("/login") // 로그인 엔드포인트
//    public ResponseDto<?> login(@RequestBody LoginDTO loginDto) {
//        return userService.login(loginDto); // UserService의 login 메소드 호출
//    }

    @GetMapping("/info")
    public ResponseDto<?> getMethodName(Authentication authentication) {
        return userService.getUserInfo(authentication);
    }

    @GetMapping("/test")
    public String getMethodName() {
        System.out.println("test");
        return "test";
    }
    
}