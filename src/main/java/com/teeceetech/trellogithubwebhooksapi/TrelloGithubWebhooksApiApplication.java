package com.teeceetech.trellogithubwebhooksapi;

import com.teeceetech.trellogithubwebhooksapi.config.TrelloConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TrelloConfigProperties.class)
public class TrelloGithubWebhooksApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrelloGithubWebhooksApiApplication.class, args);
	}

}
