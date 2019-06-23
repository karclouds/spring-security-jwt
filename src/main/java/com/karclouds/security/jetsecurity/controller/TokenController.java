package com.karclouds.security.jetsecurity.controller;


import com.karclouds.security.jetsecurity.model.JwtUser;
import com.karclouds.security.jetsecurity.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping
    public String generate(@RequestBody final JwtUser jwtUser) {
        return jwtGenerator.generate(jwtUser);
    }
}
