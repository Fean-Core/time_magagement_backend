package com.timemanagement.controller;

import com.timemanagement.dto.auth.AuthResponse;
import com.timemanagement.dto.auth.LoginRequest;
import com.timemanagement.dto.auth.LogoutResponse;
import com.timemanagement.dto.auth.RegisterRequest;
import com.timemanagement.dto.auth.UserDto;
import com.timemanagement.model.User;
import com.timemanagement.security.JwtTokenUtil;
import com.timemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setName(request.getName());
            
            User savedUser = userService.createUser(user);
            
            String token = jwtTokenUtil.generateToken(savedUser.getEmail());
            
            UserDto userDto = new UserDto(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getAvatar()
            );
            
            AuthResponse response = new AuthResponse(token, null, userDto);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Optional<User> userOpt = userService.findByEmail(request.getEmail());
            
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            
            User user = userOpt.get();
            
            if (!userService.validatePassword(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            
            String token = jwtTokenUtil.generateToken(user.getEmail());
            
            UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getAvatar()
            );
            
            AuthResponse response = new AuthResponse(token, null, userDto);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        System.out.println("DEBUG - Logout endpoint called");
        
        // In a stateless JWT application, logout is typically handled client-side
        // by removing the token from storage. This endpoint exists for compatibility.
        LogoutResponse response = new LogoutResponse("Logout successful");
        
        System.out.println("DEBUG - Logout response created successfully");
        return ResponseEntity.ok(response);
    }
}
