package com.example.financialapp.infrastructure.logging;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        String cid = Optional.ofNullable(req.getHeader("x-correlation-id"))
                .orElse(UUID.randomUUID().toString());
        MDC.put("correlationId", cid);
        resp.setHeader("x-correlation-id", cid);
        try{
            chain.doFilter(request, response);
        }finally {
            MDC.clear();
        }
    }
}
