package techfair_comon.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private Long userNo;        // PK: 사용자 ID
    private String userTel;     // 전화번호
    private String userPw;      // 비밀번호
    private String userName;     // 이름
    private String userId;      // 사용자 ID
    private String confirmUserPw; // 비밀번호 확인 필드 추가
    private String certificationCode; // 사용자 입력 인증번호 필드
}
