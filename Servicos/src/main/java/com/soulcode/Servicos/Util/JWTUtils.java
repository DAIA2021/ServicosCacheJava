package com.soulcode.Servicos.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("86400000")
    private Long expiration;

    public String generateToken (String email){
        return JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration)) //Pegar a data atual do servidor + tempo determinado do token
                .sign(Algorithm.HMAC512(secret));
    }

    public  String getLogin (String token){
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
                .getSubject();
    }

}



