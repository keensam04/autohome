package com.autohome.filter;

import com.autohome.config.JwtTokenUtil;
import com.autohome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter() {
        super("/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw  new AuthenticationException("No bearer token found in authorization header"){};
        }

        String authToken = authHeader.substring(7);
        Optional<User> user = jwtTokenUtil.parseToken(authToken);

        if (user.isPresent()) {
            User _user = user.get();
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(String.format("ROLE_%s", _user.getRole().toUpperCase())));
            Authentication auth = new AbstractAuthenticationToken(authorities) {
                @Override
                public Object getCredentials() {
                    return "";
                }

                @Override
                public Object getPrincipal() {
                    return _user;
                }
            };
            return getAuthenticationManager().authenticate(auth);
        }
        throw  new AuthenticationException("Invalid authorization token"){};
    }
}
