package techfair_comon.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import techfair_comon.user.enums.Grade;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service("userDetailService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(Long idx, String nickname, Grade grade) throws UsernameNotFoundException {
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(grade.label()));

        return new UserContext(idx, nickname, grade, roles);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}