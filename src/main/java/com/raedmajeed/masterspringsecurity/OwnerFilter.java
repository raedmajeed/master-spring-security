package com.raedmajeed.masterspringsecurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class OwnerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String OWNER_NAME = "x-owner-token";
        boolean contains = Collections.list(request.getHeaderNames()).contains(OWNER_NAME);

        if (!contains) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(request.getHeader("x-owner-token"));
        if (!request.getHeader("x-owner-token").equals("raed")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().println("THIS IS NOT RAED");
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-type", "text/plain;charset=utf-8");
            return;
        }

        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(new OwnerAuthentication());
        SecurityContextHolder.setContext(emptyContext);
        filterChain.doFilter(request, response);

    }
}
