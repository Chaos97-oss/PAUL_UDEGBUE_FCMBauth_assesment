package com.example.auth.dto;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private Long userId;
    private String username;
    private String roles;
    private long expiresIn;

    public AuthResponse(String accessToken, Long userId, String username, String roles, long expiresIn) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.expiresIn = expiresIn;
    }

    public AuthResponse(String accessToken, String tokenType, Long userId, String username, String roles,
            long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.expiresIn = expiresIn;
    }

    public AuthResponse() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
