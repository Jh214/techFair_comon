package techfair_comon.config.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import techfair_comon.config.auth.PrincipalDetails;
import techfair_comon.entity.User;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticateManager;

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {

        try {

            System.out.println("Login");

            ObjectMapper mapper = new ObjectMapper();

            User user = mapper.readValue(request.getInputStream(), User.class);
            
            System.out.println("User Name :: " + user.getUserId());
            System.out.println("Password :: " + user.getUserPw());

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPw());
            
                Authentication authentication = authenticateManager.authenticate(authenticationToken);

                PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

                return authentication;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
		Authentication authResult
    ) throws IOException, ServletException {

        JwtProperty jwtProperty = new JwtProperty();

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = Jwts.builder()
            .setSubject("Token")
            .claim("auth", "ADMIN")
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperty.SECRET)), SignatureAlgorithm.HS512)
            .setId(principalDetails.getUser().getUserNo().toString())
            .compact();
        
        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
    
}
