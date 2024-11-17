//package com.alex.Notes_Application_BE.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class RequestValidationFilter extends OncePerRequestFilter {
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String header = request.getHeader("X-Valid-Request");
//        String clientIp = request.getRemoteAddr();
//        System.out.println("##################### CLIENT IP ################");
//        System.out.println(clientIp);
//        if (header == null || !header.equals("true")) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
//            logger.error("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Invalid request, Provide X-Valid-Request header >>>>>>>>>>>>>>>>>");
//            return;
//        }
//        filterChain.doFilter(request, response);
//    }
//}