package techfair_comon.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import techfair_comon.entity.User;

public class PrincipalDetails implements UserDetails {

	private User user;

    /**
	 * 
	 * @param user
	 */
	public PrincipalDetails(User user) {
		this.user = user;
	}

    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
//
//        return grantedAuthorities;
		return null;
    }

    @Override
    public String getPassword() {
        
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        
        return user.getUserId();
    }
    
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
}
