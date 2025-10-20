package com.gary.springsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter2 extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("執行 MyFilter2");

        if (request.getRequestURI().contains("/hello")) {
            System.out.println("允許通過");
            // 傳遞給下一個Filter，繼續處理
            filterChain.doFilter(request, response);
        } else {
            System.out.println("不允許通過，中斷請求");
            response.setStatus(500);
        }


    }
}
