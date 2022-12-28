package com.trollmarket.config.authentication;

import com.trollmarket.exceptionhandler.CustomAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String selectedRole = request.getParameter("role");
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if(selectedRole != null) {
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
                throw new CustomAuthenticationException("Invalid Username or Password");

            } else if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())
                    && !selectedRole.equals(userDetails.getAuthorities().toArray()[0].toString())) {
                throw new CustomAuthenticationException("Role is Invalid");

            } else {
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                        , userDetails.getPassword()
                        , userDetails.getAuthorities());
            }
        }

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {

            throw new CustomAuthenticationException("Invalid Username or Password");

        } else if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())
                && !authentication
                    .getAuthorities().toArray()[0].toString()
                    .equals(userDetails.getAuthorities().toArray()[0].toString())) {

            throw new CustomAuthenticationException("Role is Invalid");

        } else {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                    , userDetails.getPassword()
                    , userDetails.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
