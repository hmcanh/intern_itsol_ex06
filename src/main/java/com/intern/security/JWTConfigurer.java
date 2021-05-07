package com.intern.security;

import com.intern.security.TokenProvider;
import com.intern.security.jwt.JWTFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    public JWTConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        // Khởi tạo JWTFilter
 JWTFilter customFilter = new JWTFilter(tokenProvider);

        // Thêm filter của mình vào HttpSecurity
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}