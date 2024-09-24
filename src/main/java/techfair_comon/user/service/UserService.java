package techfair_comon.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import techfair_comon.entity.User; // User 엔티티 임포트
import techfair_comon.user.dto.LoginDTO; // LoginDTO 임포트
import techfair_comon.user.dto.SignupDTO; // SignupDTO 임포트
import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.user.repository.UserRepository; // UserRepository 임포트

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // UserRepository 주입

    // 사용자 가입 메소드
    public ResponseDto<Void> signup(SignupDTO signupDTO) {
        User user = new User();
        user.setUserId(signupDTO.getUserId());
        user.setUserPw(signupDTO.getUserPw());
        user.setUserName(signupDTO.getUserName());
        user.setUserTel(signupDTO.getUserTel());

        // 사용자 정보를 데이터베이스에 저장
        try {
            userRepository.save(user); // UserRepository를 통해 사용자 저장
        } catch (DataIntegrityViolationException e) {
            return ResponseDto.setFailed("사용자 ID가 이미 존재합니다."); // 중복 ID 처리
        }

        return ResponseDto.setSuccess("회원가입 성공");
    }

    // 로그인 메소드
    public ResponseDto<Void> login(LoginDTO loginDTO) {
        // 입력 검증
        if (loginDTO.getUserNo() == null || loginDTO.getUserPw() == null) {
            return ResponseDto.setFailed("아이디 또는 비밀번호를 입력하세요.");
        }

        // 사용자 정보 조회
        Optional<User> byId = userRepository.findById(loginDTO.getUserNo());// ID로 사용자 조회
// 비밀번호 확인
        if (byId.isPresent() ) {
            if (byId.get().getUserPw().equals(loginDTO.getUserPw())) {
                return ResponseDto.setSuccess("로그인 성공");
            }else {
                return ResponseDto.setFailed("로그인 실패");
            }

        }else {
            return ResponseDto.setFailed("로그인 실패");
        }

    }
}
