package techfair_comon.bg.controller;

import org.springframework.security.core.Authentication;
import techfair_comon.entity.User;
import techfair_comon.security.service.UserContext;

public class DecodeJwt {
    public static User toUserNo(Authentication authentication) {
        if(authentication == null) return null;
        UserContext userContext = (UserContext) authentication.getPrincipal();
        return User.builder()
                .userNo(userContext.getUserNo())
                .build();
    }
}
