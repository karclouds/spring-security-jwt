package com.karclouds.security.jetsecurity.security;

import com.karclouds.security.jetsecurity.model.JwtAuthenticationToken;
import com.karclouds.security.jetsecurity.model.JwtUser;
import com.karclouds.security.jetsecurity.model.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticatorProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }


    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;

        String token = jwtToken.getToken();

        JwtUser jwtUser= validator.validate(token);

        if(jwtUser == null) throw new RuntimeException("JwtUser is null");

        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole()) ;

        return new JwtUserDetails(jwtUser.getUserName(), jwtUser.getId(), token, grantedAuthorityList);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
