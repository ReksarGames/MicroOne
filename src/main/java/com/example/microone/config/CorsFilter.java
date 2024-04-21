package com.example.microone.config;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Здесь можно инициализировать любые ресурсы для фильтра, если это необходимо
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("*", "*");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // Здесь можно освободить ресурсы, если это необходимо
    }
}
