package com.autohome.config;

import com.autohome.service.CustomUserOidcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserOidcService userOidcService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       /* http.authorizeRequests()
                .antMatchers("/","/ping","/login","/error").permitAll()
                .anyRequest().authenticated().and().userDetailsService();*/

        http
                .authorizeRequests().antMatchers("/ping","/ping/*", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().userInfoEndpoint().oidcUserService(userOidcService);
    }
}
