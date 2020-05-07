package com.autohome.config;

import com.autohome.dao.UsersRepo;
import com.autohome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            return;
        }
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        Map<String,Object> attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");
        User user = usersRepo.getUserByEmail(email);
        String token = jwtTokenUtil.generateToken(user);

        String redirectionUrl = UriComponentsBuilder.fromUriString("http://localhost:8111/").queryParam("auth_token", token).build().toString();
        getRedirectStrategy().sendRedirect(request,response,redirectionUrl);
    }
}
