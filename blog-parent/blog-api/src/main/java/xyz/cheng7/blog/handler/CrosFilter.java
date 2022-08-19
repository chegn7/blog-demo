package xyz.cheng7.blog.handler;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class CrosFilter implements javax.servlet.Filter {

    private Set<String> allowOrigins;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigins = new HashSet<>();
        allowOrigins.add("http://localhost:8080");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String allowOrigin = ((HttpServletRequest) request).getHeader("Origin");
        if (allowOrigins.contains(allowOrigin)) {
            String allowHeader = ((HttpServletRequest) request).getHeader("Access-Control-Request-Headers");
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", allowOrigin);
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", allowHeader);
            //设置允许带cookie的请求
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Credentials", "true");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
