package techfair_comon.config.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import techfair_comon.config.auth.PrincipalDetails;
import techfair_comon.entity.User;
import techfair_comon.user.repository.UserRepository;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        JwtProperty jwtProperty = new JwtProperty();

        String jwtHeader = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(jwtHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            
            String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
            System.out.println(jwtToken);
            String userNo = Jwts.parser()
                .setSigningKey(jwtProperty.SECRET)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getId();

            System.out.println(userNo);
            Optional<User> user = userRepository.findById(Long.parseLong(userNo));

            if (user.isEmpty()) {
                return;
            }
            System.out.println(user.get().getUserName());
            PrincipalDetails principalDetails = new PrincipalDetails(user.get());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails.getUser(),
                    principalDetails.getPassword(),
                    principalDetails.getAuthorities()
                );
                
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("인증완료");

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }

    
}
