package com.karclouds.security.jetsecurity.security;

import com.karclouds.security.jetsecurity.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private String secret = "youtube";

    public JwtUser validate(String token) {
        JwtUser jwtUser = null;
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            jwtUser = new JwtUser();
            jwtUser.setUserName(claims.getSubject());
            jwtUser.setId(Long.parseLong((String)claims.get("userId")));
            jwtUser.setRole((String)claims.get("role"));

        }catch (Exception e){
            System.out.println(e);
        }

        return jwtUser;
    }

}
