package com.example.msplayer.player;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor{ /*implements RequestInterceptor {

 /*   @Override
    public void apply(RequestTemplate requestTemplate) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        requestTemplate.header("Authorization", "Bearer " + token);
    }*/
}

