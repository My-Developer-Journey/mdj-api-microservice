package com.diemyolo.auth_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/authentications")
@RestController
public class AuthenticationController {

    @Value("${jwt.expiration}")
    private long jwtExpiration;

}
