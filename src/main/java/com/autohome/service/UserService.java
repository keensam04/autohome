package com.autohome.service;

import com.autohome.dao.UsersRepo;
import com.autohome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService extends OidcUserService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        User user = new User();
        user.setEmail((String)attributes.get("email"));
        user.setPicture((String) attributes.get("picture"));
        user.setFirstName((String) attributes.get("given_name"));
        user.setLastName((String) attributes.get("family_name"));
        updateUser(user);
        return oidcUser;
    }

    private void updateUser(User user){
        User userByEmail = usersRepo.getUserByEmail(user.getEmail());
        if(userByEmail == null)
            usersRepo.addUser(user);
    }
}
