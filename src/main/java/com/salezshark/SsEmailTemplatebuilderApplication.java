package com.salezshark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.salezshark.emailtemplate.service.ICachingService;

@SpringBootApplication
@EnableCaching
public class SsEmailTemplatebuilderApplication  implements CommandLineRunner{

	@Autowired
	private ICachingService cachingService;
	
	public static void main(String[] args) {
		SpringApplication.run(SsEmailTemplatebuilderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cachingService.cachedDataSourceMapping();
	}

}
