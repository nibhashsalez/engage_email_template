package com.salezshark.emailtemplate.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.salezshark.emailtemplate.interceptor.TenantInterceptor;

/**
 * @author smoyeen
 * @version 4.0
 * @since 4.0
 */

@Configuration
public class EmailTemplateWebConfig implements WebMvcConfigurer {

	@Autowired
	private TenantInterceptor tenantInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(tenantInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**").allowedHeaders("dsmID","dsmid", "Content-Type","content-type","authorization","access_token",
				"accessToken","refreshToken","Authorization","timeZone")
		.allowedOrigins("*").
		allowedMethods("PUT","DELETE", "OPTIONS", "GET", "POST","PATCH");
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
