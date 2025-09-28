package org.example.helloworld.controller;

import org.example.helloworld.dto.LoginRequest;
import org.example.helloworld.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // TODO: Thay bằng kiểm tra user/password trong database
        if ("lap".equals(request.getUsername()) && "123".equals(request.getPassword())) {
            return jwtUtil.generateToken(request.getUsername());
        }
        return "Invalid credentials";
    }
}
