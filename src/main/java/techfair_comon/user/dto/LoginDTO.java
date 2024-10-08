package techfair_comon.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String userId; // 회원 ID (문자열)
    private String userPw; // 비밀번호
}
