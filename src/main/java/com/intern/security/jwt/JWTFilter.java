package com.intern.security.jwt;

import com.intern.security.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // Lấy ra request
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // Lấy ra chuỗi JWT trong header của request
        String jwt = resolveToken(httpServletRequest);

        // Validate JWT Token
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            // Tạo Authentication từ chuỗi JWT
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);

            // Set Authentication vào SecurityContextHolder: Thông báo với Spring Security là Authen thành công
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tiếp tục thực hiện request
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            // Khi JWT không hợp lệ, từ chối request ===> Trả về lỗi 401 SC_UNAUTHORIZED
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
            return;
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}