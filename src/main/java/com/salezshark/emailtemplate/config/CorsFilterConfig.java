package com.salezshark.emailtemplate.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter{

@SuppressWarnings("unused")
private FilterConfig config;

   public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
   public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
   public static final String METHODS_NAME = "Access-Control-Allow-Methods";
   public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
   public static final String MAX_AGE_NAME = "Access-Control-Max-Age";

   @Override
   public void destroy() {

   }

   @Override
   public void doFilter(ServletRequest req, ServletResponse resp,
                        FilterChain chain) throws IOException, ServletException {
       HttpServletResponse response = (HttpServletResponse) resp;
       HttpServletRequest request = (HttpServletRequest) req;
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT,PATCH");
       response.setHeader("Access-Control-Max-Age", "3600");
       response.setHeader("Access-Control-Allow-Headers", "dsmID,dsmid,Content-Type,content-type,authorization,access_token,accessToken,refreshToken,Authorization,timeZone");

       if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
           response.setStatus(HttpServletResponse.SC_OK);
       } else {
           chain.doFilter(req, resp);
       }

   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
       config = filterConfig;
   }
}


