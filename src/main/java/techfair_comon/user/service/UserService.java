/*
    작성자: 이동주
    버전: 17
    기능: 로그인, 회원가입

    작성자: 김주현
    버전: 17
    기능: 회원조회, 수정

 */

package techfair_comon.user.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import techfair_comon.entity.User; // User 엔티티 임포트
import techfair_comon.user.dto.LoginDTO; // LoginDTO 임포트
import techfair_comon.user.dto.SignupDTO; // SignupDTO 임포트
import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.user.repository.UserRepository; // UserRepository 임포트

import java.util.Optional;

@Service
@AllArgsConstructor

public class UserService {

    //@Autowired
    private final UserRepository userRepository; // UserRepository 주입

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); // 비밀번호 해싱을 위한 인코더

    // 사용자 가입 메소드
    public ResponseDto<Void> signup(SignupDTO signupDTO) {
        // 닉네임 필드 확인
        if (signupDTO.getNickname() == null || signupDTO.getNickname().isEmpty()) {
            return ResponseDto.setFailed("닉네임을 입력하세요.");
        }
        // 사용자 이름 확인
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()) {
            return ResponseDto.setFailed("사용자 이름을 입력하세요."); // 사용자 이름 입력 확인 추가
        }


        // 전화번호 인증 로직 (API가 준비되면 추가)
        // TODO: 전화번호 인증 API 호출 로직 추가

        // 비밀번호 검증
        String userPw = signupDTO.getUserPw();
        String confirmUserPw = signupDTO.getConfirmUserPw(); // confirmUserPw 필드를 SignupDTO에 추가했을 경우
        if (!userPw.equals(confirmUserPw)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        if (!isValidPassword(userPw)) {
            return ResponseDto.setFailed("비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
        }

        // 사용자 객체 생성 및 저장
        User user = new User();
        user.setUserId(signupDTO.getUserId());
        user.setUserPw(bCryptPasswordEncoder.encode(userPw)); // 비밀번호 해싱
        user.setUserName(signupDTO.getUserName());
       // user.setUserTel(signupDTO.getUserTel());
        user.setUserTel(bCryptPasswordEncoder.encode(signupDTO.getUserTel()));


        // 데이터베이스에 저장
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseDto.setFailed("사용자 ID가 이미 존재합니다.");
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
        Optional<User> byId = userRepository.findById(loginDTO.getUserNo()); // ID로 사용자 조회
        // 비밀번호 확인
        if (byId.isPresent()) {
            if (bCryptPasswordEncoder.matches(loginDTO.getUserPw(), byId.get().getUserPw())) {
                return ResponseDto.setSuccess("로그인 성공");
            } else {
                return ResponseDto.setFailed("로그인 실패");
            }
        } else {
            return ResponseDto.setFailed("로그인 실패");
        }
    }

    // 비밀번호 유효성 검사 메소드
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }
    // **회원 조회 메소드**
    public ResponseDto<User> getUserInfo(Long userNo) {

        return null;
    }
}