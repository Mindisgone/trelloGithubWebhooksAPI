package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.GhRoot;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Card;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RestController
public class Github {
    @Value("${trello.key}")
            private String trelloKey;

    @Value("${trello.token}")
            private String trelloToken;

    Logger logger = LoggerFactory.getLogger(Github.class);

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public Github(){}

    @RequestMapping(value = "/api/github", method = RequestMethod.POST)
    public void receiveGithubMessage(@RequestBody GhRoot message){
        if (message.action != null && message.getAction().equals("opened")){
            logger.info("PR OPENED, url = " + message.pull_request.url);
            logger.info("PR OPENED, id = " + message.pull_request.number);
            logger.info("PR OPENED, author is = " + message.pull_request.assignee.login);
            logger.info("PR OPENED, branch is = " + message.pull_request.head.ref);

            logger.info("trello key = " + trelloKey);
            logger.info("trello token = " + trelloToken);
            logger.info("Card ID is = " + getCardId(message.pull_request.head.ref));
        }

        if (message.action != null && message.getAction().equals("closed") && message.pull_request.merged){
            logger.info("PR CLOSED, url = " + message.pull_request.url);
            logger.info("PR CLOSED, id = " + message.pull_request.number);
            logger.info("PR CLOSED, author is = " + message.pull_request.merged_by.login);
            logger.info("PR CLOSED, branch is = " + message.pull_request.head.ref);

            logger.info("trello key = " + trelloKey);
            logger.info("trello token = " + trelloToken);
            logger.info("Card ID is = " + getCardId(message.pull_request.head.ref));
        }
    }

    private String getCardId(String name) {
        String cardId = "";
        String url = "https://api.trello.com/1/search?modelTypes=cards&query=" +
                name + "&key=" + trelloKey + "&token=" + trelloToken;
        // require non null
        List<Card> cards = Objects.requireNonNull(restTemplate.getForObject(url, Root.class)).cards;

        if (cards.size() == 1){
            cardId = cards.get(0).id;
        }

        return cardId;
    }
}
