package com.example.management.filter;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AuthoritiesLoggingfAfterFilter implements Filter {

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingfAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOG.info("User " + authentication.getName() + " is sucessfully authenticated and has the authorities "
                    + authentication.getAuthorities().toString());
        }

        chain.doFilter(request, response);

    }
}
