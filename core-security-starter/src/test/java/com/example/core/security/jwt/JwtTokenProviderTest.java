package com.example.core.security.jwt;

import com.example.core.security.userdetails.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private final long expiration = 3600000;

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        when(jwtProperties.getSecretKey()).thenReturn(secretKey);
        
        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    void testGenerateTokenWithSecurityUser() {
        when(jwtProperties.getExpirationMs()).thenReturn(expiration);
        
        SecurityUser securityUser = Mockito.mock(SecurityUser.class);
        when(securityUser.getId()).thenReturn(1L);
        when(securityUser.getUsername()).thenReturn("user");
        
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(securityUser).getAuthorities();
        
        when(authentication.getPrincipal()).thenReturn(securityUser);

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("user", jwtTokenProvider.getUsernameFromJWT(token));
    }

    @Test
    void testGenerateTokenWithUserDetails() {
        when(jwtProperties.getExpirationMs()).thenReturn(expiration);
        
        UserDetails userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtTokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals("user", jwtTokenProvider.getUsernameFromJWT(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalidToken"));
    }
}
