package com.teeceetech.trellogithubwebhooksapi;

import com.teeceetech.trellogithubwebhooksapi.config.TrelloConfigProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(TrelloConfigProperties.class)
class TrelloGithubWebhooksApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
