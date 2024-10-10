package techfair_comon.user.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import techfair_comon.entity.User;
import techfair_comon.user.dto.LoginDTO;
import techfair_comon.user.dto.SignupDTO;
import techfair_comon.ResponseDto;
import techfair_comon.user.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final CoolSmsService coolSmsService;

    // 사용자 가입 메소드
    public ResponseDto<Void> signup(SignupDTO signupDTO) {
        // 사용자 이름 확인
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()) {
            return ResponseDto.setFailed("사용자 이름을 입력하세요.");
        }

        // 전화번호 확인
        if (signupDTO.getUserTel() == null || signupDTO.getUserTel().isEmpty()) {
            return ResponseDto.setFailed("전화번호를 입력하세요.");
        }



        // 비밀번호 검증
        String userPw = signupDTO.getUserPw();
        String confirmUserPw = signupDTO.getConfirmUserPw();
        if (!userPw.equals(confirmUserPw)) {
            return ResponseDto.setFailed("비밀번호가 일치하지 않습니다.");
        }

        if (!isValidPassword(userPw)) {
            return ResponseDto.setFailed("비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.");
        }
        // 전화번호 인증 로직 (CoolSMS API를 통해 인증번호 전송)
        String sendResult = coolSmsService.sendCertificationCode(signupDTO.getUserTel()); // 입력한 전화번호로 인증번호 전송
        if (!sendResult.equals("인증번호가 발송되었습니다.")) {
            return ResponseDto.setFailed("인증번호 전송에 실패했습니다.");
        }

        // 사용자 객체 생성 및 저장
        User user = new User();
        user.setUserId(signupDTO.getUserId());
        user.setUserPw(bCryptPasswordEncoder.encode(userPw));
        user.setUserName(signupDTO.getUserName());
        user.setUserTel(signupDTO.getUserTel()); // 전화번호 저장

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
        if (loginDTO.getUserId() == null || loginDTO.getUserPw() == null) {
            return ResponseDto.setFailed("아이디 또는 비밀번호를 입력하세요.");
        }

        // 사용자 정보 조회 (User를 반환하도록 수정)
        User byUserId = userRepository.findByUserId(loginDTO.getUserId()); // userId로 사용자 조회
        if (byUserId == null) {
            return ResponseDto.setFailed("로그인 실패: 사용자 ID가 존재하지 않습니다.");
        }

        // 비밀번호 확인
        if (bCryptPasswordEncoder.matches(loginDTO.getUserPw(), byUserId.getUserPw())) {
            return ResponseDto.setSuccess("로그인 성공");
        } else {
            return ResponseDto.setFailed("로그인 실패: 비밀번호가 일치하지 않습니다.");
        }
    }

    // 비밀번호 유효성 검사 메소드
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    // 회원 조회 메소드
    public ResponseDto<User> getUserInfo(Long userNo) {
        // 사용자 정보 조회 로직
        Optional<User> user = userRepository.findById(userNo);

        return null;  // 실제 구현 필요
    }
}
