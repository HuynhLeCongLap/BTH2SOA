package org.example.helloworld.controller;

import org.example.helloworld.dto.LoginRequest;
import org.example.helloworld.entity.User;
import org.example.helloworld.repository.UserRepository;
import org.example.helloworld.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== LOGIN ATTEMPT ===");
            System.out.println("Username: " + request.getUsername());
            
            // Kiểm tra input
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            
            // Tìm user trong database
            User user = userRepository.findByUserName(request.getUsername().trim());
            
            if (user == null) {
                System.out.println("User not found: " + request.getUsername());
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
            
            System.out.println("User found: " + user.getUserName());
            
            // Kiểm tra password
            if (!user.getPassword().equals(request.getPassword().trim())) {
                System.out.println("Password mismatch for user: " + user.getUserName());
                return ResponseEntity.badRequest().body("Invalid credentials");
            }
            
            // Tạo JWT token
            String token = jwtUtil.generateToken(user.getUserName());
            
            // Lưu token vào database
            user.setToken(token);
            userRepository.save(user);
            
            System.out.println("Login successful for user: " + user.getUserName());
            
            return ResponseEntity.ok().body(token);
            
        } catch (Exception e) {
            System.out.println("ERROR in login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Authorization header missing or invalid");
            }
            
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (jwtUtil.validateToken(token, username)) {
                return ResponseEntity.ok().body("Token is valid for user: " + username);
            } else {
                return ResponseEntity.badRequest().body("Invalid or expired token");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR in token validation: " + e.getMessage());
            return ResponseEntity.badRequest().body("Token validation failed: " + e.getMessage());
        }
    }
}
