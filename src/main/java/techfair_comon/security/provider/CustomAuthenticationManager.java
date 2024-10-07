package techfair_comon.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import techfair_comon.entity.User;
import techfair_comon.security.service.CustomUserDetailsServiceImpl;
import techfair_comon.security.token.CustomAuthenticationToken;
import techfair_comon.user.repository.UserRepository;

import java.util.Optional;

import static io.jsonwebtoken.lang.Strings.hasText;


public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); // 비밀번호 해싱을 위한 인코더

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) authentication;

        String userNo = customAuthenticationToken.getUserNo();
        String userPw = customAuthenticationToken.getUserPw();

        User user = userRepository.findById(Long.valueOf(userNo))
                .orElseThrow(() -> new BadCredentialsException("로그인에 실패 했습니다."));

        if (!bCryptPasswordEncoder.matches(userPw, user.getUserPw())) {
            throw new BadCredentialsException("로그인에 실패 했습니다.");
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserNo(), user.getUserName(), user.getRole());
        if (userDetails == null) {
            throw new BadCredentialsException("로그인에 실패 했습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}









