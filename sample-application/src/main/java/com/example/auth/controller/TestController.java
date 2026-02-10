package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final com.example.auth.repository.UserRepository userRepository;

    public TestController(com.example.auth.repository.UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/public/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Application is running. Status: Healthy");
    }

    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("Authenticated User: " + userDetails.getUsername());
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<java.util.List<com.example.auth.dto.UserDto>> getAdminData() {
        java.util.List<com.example.auth.dto.UserDto> users = userRepository.findAll().stream()
                .map(user -> new com.example.auth.dto.UserDto(user.getId(), user.getUsername(), user.getRoles()))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
