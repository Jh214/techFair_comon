package techfair_comon.security.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import techfair_comon.user.enums.Grade;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserContext extends User {
    private final Long userNo;
    private final String nickname;
    private final Grade grade;

    public UserContext(Long userNo, String nickname, Grade grade, Collection<? extends GrantedAuthority> authorities) {
        super(String.valueOf(userNo), "beeb.tripcoach", authorities);
        this.userNo = userNo;
        this.grade = grade;
        this.nickname = nickname;
    }
}