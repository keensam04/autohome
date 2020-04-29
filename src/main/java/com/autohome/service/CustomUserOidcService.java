package com.autohome.service;

import com.autohome.dao.UsersRepo;
import com.autohome.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomUserOidcService extends OidcUserService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        Users users = new Users();
        users.setEmail((String)attributes.get("email"));
        users.setPicture((String) attributes.get("picture"));
        users.setFirstName((String) attributes.get("given_name"));
        users.setLastName((String) attributes.get("family_name"));
        updateUser(users);
        return oidcUser;
    }

    private void updateUser(Users users){

    }
}
