package techfair_comon.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long userNo;    // PK: 사용자 ID
    private String userTel; // 전화번호
    private String userPw;  // 비밀번호
    private String userName; // 이름
    private String userEmail; // 이메일 추가

    // 기본 생성자
    public UserDTO() {}
}
