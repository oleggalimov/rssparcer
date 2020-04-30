package org.oleggalimov.rssreader;

import org.oleggalimov.rssreader.dto.ExtractingRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
public class RssReaderApplication {
	@Bean
	public ConcurrentHashMap <String, ExtractingRule> feedMap() {
		return new ConcurrentHashMap<>();
	}
	public static void main(String[] args) {
		SpringApplication.run(RssReaderApplication.class, args);
	}

}
