package techfair_comon.security.token;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final String userNo;
    private final String userPw;

    public CustomAuthenticationToken(String userNo, String userPw) {
        super(null);
        this.userNo = userNo;
        this.userPw = userPw;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userNo;
    }


}
