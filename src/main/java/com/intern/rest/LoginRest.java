package com.intern.rest;

import com.intern.dto.LoginDTO;
import com.intern.security.TokenProvider;
import com.intern.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LoginRest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO loginDTO) {

        try {
            // Tạo input đầu vào cho Spring security
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

            // Thực hiện Authen bằng authenticationManagerBuilder của Spring
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // Thông tin đăng nhập chính xác Spring trả về Authentication và mình set vào SecurityContextHolder để xác nhận
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo chuỗi JWT bằng tokenProvider
            String jwt = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            // Trả về kết quả
            return new ResponseEntity<JWTToken>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        } catch (Exception ex) {
            // Khi thông tin đăng nhập sai Spring Security sẽ throw ra Exception và chúng ta catch Ex đó để thông báo lỗi đăng nhập sai
            return new ResponseEntity<>(new JWTToken(), HttpStatus.UNAUTHORIZED);
        }
    }

    static class JWTToken {

        private String idToken;
        private String role;

        public JWTToken() {
        }

        public JWTToken(String idToken) {
            this.idToken = idToken;
        }

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}