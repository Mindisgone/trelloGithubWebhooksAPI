package com.teeceetech.trellogithubwebhooksapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

@RestClientTest(TrelloService.class)
class TrelloServiceTest {

    @Autowired
    private TrelloService trelloService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getCardId() {
        /* String name = "nameTest";
        String trelloKey = "trelloKeyTest";
        String token = "tokenTest";

        String url = "https://api.trello.com/1/search?modelTypes=cards&query=" +
                name +
                "&key=" +
                trelloKey +
                "&token=" +
                token;

        mockRestServiceServer.expect(requestTo(url)).andRespond(withServerError());

        assertEquals(anyString(), trelloService.getCardId(name,trelloKey,token)); */
    }

    @Test
    void addCardAttachment() {
    }

    @Test
    void addCardComment() {
    }
}