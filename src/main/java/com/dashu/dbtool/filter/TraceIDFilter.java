package com.dashu.dbtool.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIDFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String traceID = UUID.randomUUID().toString().replace("-", "");
            MDC.put("traceID", traceID);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove("traceID");
        }
    }
}
