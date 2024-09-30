package techfair_comon.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import techfair_comon.entity.User;
import techfair_comon.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = userRepository.findByUserId(username);
			
			if (ObjectUtils.isEmpty(user)) {
				throw new UsernameNotFoundException(username);
			}
			
			userRepository.save(user);
			
			return new PrincipalDetails(user);
    }

}
