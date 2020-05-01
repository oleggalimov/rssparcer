package org.oleggalimov.rssreader;

import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
public class RssReaderApplication implements WebMvcConfigurer {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(
				"/css/**",
				"/img/**",
				"/js/**",
				"/vendor/**"
		)
				.addResourceLocations(
						"classpath:/static/css/",
						"classpath:/static/img/",
						"classpath:/static/js/",
						"classpath:/static/vendor/"
				);
	}

	@Bean
	public ConcurrentHashMap <String, ExtractingRule> feedMap() {
		return new ConcurrentHashMap<>();
	}
	public static void main(String[] args) {
		SpringApplication.run(RssReaderApplication.class, args);
	}

}
