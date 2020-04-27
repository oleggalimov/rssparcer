package org.oleggalimov.rssreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RssReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssReaderApplication.class, args);
	}

}
