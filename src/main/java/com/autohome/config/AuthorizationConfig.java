package com.autohome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

@Configuration
//@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = System.getProperty("clientId");
    private static final String CLIENT_SECRET = System.getProperty("clientSecret");

}
