package techfair_comon.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private Long userNo; // 회원 ID
    private String userPw; // 비밀번호
}