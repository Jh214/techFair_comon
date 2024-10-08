/*
    작성자: 이동주
    버전: 18
    기능: 로그인, 회원가입, 전화번호 인증

    작성자: 김주현
    버전: 18
    기능: 회원조회, 수정
 */

package techfair_comon.user.service;

<<<<<<< HEAD

=======
import lombok.AllArgsConstructor;
>>>>>>> 2b5d65dc767812639ba2513b1bd1b56fab63e82e
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import techfair_comon.ResponseDto; // ResponseDto 임포트
import techfair_comon.entity.User; // User 엔티티 임포트
import techfair_comon.security.service.UserContext;
import techfair_comon.user.dto.SignupDTO; // SignupDTO 임포트
import techfair_comon.user.dto.UserDTO;
import techfair_comon.user.repository.UserRepository; // UserRepository 임포트

<<<<<<< HEAD
=======
import java.util.Optional;
import techfair_comon.user.service.KakaoTalkService; // KakaoTalkService 임포트
>>>>>>> 2b5d65dc767812639ba2513b1bd1b56fab63e82e

@Service
@AllArgsConstructor
public class UserService {
<<<<<<< HEAD
    private final UserRepository userRepository; // UserRepository 주입


=======

    private final UserRepository userRepository; // UserRepository 주입
>>>>>>> 2b5d65dc767812639ba2513b1bd1b56fab63e82e
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); // 비밀번호 해싱을 위한 인코더
    private final KakaoTalkService kakaoTalkService; // KakaoTalkService 주입

    // 사용자 가입 메소드
    public ResponseDto<Void> signup(SignupDTO signupDTO) {
        // 사용자 이름 확인
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()) {
            return ResponseDto.setFailed("사용자 이름을 입력하세요.");
        }

        // 전화번호 인증 로직 (카카오톡 API를 통해 인증번호 전송)
        boolean isSent = kakaoTalkService.sendCertificationCode(signupDTO.getUserTel()); // 인증번호 전송
        if (!isSent) {
            return ResponseDto.setFailed("인증번호 전송에 실패했습니다.");
        }

        // 템플릿의 #{certification}과 사용자 입력값을 비교하는 로직은 프론트엔드에서 구현됨.

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
<<<<<<< HEAD
        // user.setUserTel(signupDTO.getUserTel());
        user.setUserTel(bCryptPasswordEncoder.encode(signupDTO.getUserTel()));

=======
        user.setUserTel(signupDTO.getUserTel()); // 전화번호 저장
>>>>>>> 2b5d65dc767812639ba2513b1bd1b56fab63e82e

        // 데이터베이스에 저장
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseDto.setFailed("사용자 ID가 이미 존재합니다.");
        }

        return ResponseDto.setSuccess("회원가입 성공");
    }

    public ResponseDto<?> getUserInfo(Authentication authentication) {

       UserContext userContext = (UserContext) authentication.getPrincipal();

       User user = userRepository.findById(userContext.getUserNo()).get();

       UserDTO userDto = new UserDTO();
       userDto.setUserNo(user.getUserNo());
       userDto.setUserTel(user.getUserTel());
       userDto.setUserName(user.getUserName());

        return ResponseDto.setSuccessData("로그인 성공", userDto);
    }

    // 비밀번호 유효성 검사 메소드
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    // **회원 조회 메소드**
    public ResponseDto<User> getUserInfo(Long userNo) {
        // 사용자 정보 조회 로직
        Optional<User> user = userRepository.findById(userNo);

        return null;
    }
}
