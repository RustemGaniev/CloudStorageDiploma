package ru.netology.cloudstorage.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cloudstorage.dto.LoginDTO;
import ru.netology.cloudstorage.exception_handling.UserAuthenticationException;
import ru.netology.cloudstorage.security.JWTUtil;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authManager;

    public AuthController(JWTUtil jwtUtil, AuthenticationManager authManager) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getLogin());

            return Collections.singletonMap("auth-token", token);
        } catch (AuthenticationException authExc) {
            throw new UserAuthenticationException("Bad credentials" + authExc);
        }
    }
}