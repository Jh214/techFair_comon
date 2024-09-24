package techfair_comon.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techfair_comon.bg.dto.CreateBgDto; // CreateBgDto 임포트
import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.user.service.UserService; // UserService 임포트

@RestController
@RequestMapping("/api/user") // API 경로 설정
public class UserController {

    @Autowired
    private UserService userService; // UserService 주입

    @PostMapping("/createBg") // POST 요청을 처리하는 엔드포인트
    public ResponseDto<Void> createBg(@RequestBody CreateBgDto createBgDto) {
        return userService.createBg(createBgDto); // UserService의 createBg 메소드 호출
    }

    // 다른 메소드 (예: signup, login)도 여기에 추가 가능
}
