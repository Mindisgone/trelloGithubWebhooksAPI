package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.GhRoot;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
            buildPrOpenComment(message);
        }

        if (message.action != null && message.getAction().equals("closed") && message.pull_request.merged){
            buildPrMergedComment(message);
        }

        if (message.action != null && message.getAction().equals("created")){
            // PR title needs to be the same as branch name and trello card
            buildPrComment(message);
        }
    }

    private String getCardId(String name) {
        String cardId = "";
        String url = "https://api.trello.com/1/search?modelTypes=cards&query=" +
                name + "&key=" + trelloKey + "&token=" + trelloToken;

        Root root = restTemplate.getForObject(url, Root.class);

        if (root != null && root.cards.size() == 1){
            cardId = root.cards.get(0).id;
        } else {
            logger.error("search produced no results");
        }

        return cardId;
    }

    private void addCardComment(String ID, String text) {
        String url = "https://api.trello.com/1/cards/" +
                ID + "/actions/comments?key=" + trelloKey + "&token=" + trelloToken;
        Term term = new Term();
        term.setText(text);

        logger.info("GITHUB CONTROLLER: adding comment " + text + " to card " + ID);
        restTemplate.postForLocation(url, term);
    }

    private void buildPrOpenComment(GhRoot ghRoot) {
        String comment = "Opened PR " + ghRoot.pull_request.number + " " + ghRoot.pull_request.url;

        addCardComment(getCardId(ghRoot.pull_request.head.ref), comment);
    }

    private void buildPrMergedComment(GhRoot ghRoot) {
        String comment = "Merged PR " + ghRoot.pull_request.number + " from " +
                ghRoot.pull_request.merged_by.login + " " + ghRoot.pull_request.url;

        addCardComment(getCardId(ghRoot.pull_request.head.ref), comment);
    }

    private void buildPrComment(GhRoot ghRoot) {
        String comment = "{Github User " + ghRoot.comment.user.login + " commented " +
                ghRoot.comment.body + " on PR " + ghRoot.issue.number + " " + ghRoot.comment.html_url + "}";

        addCardComment(getCardId(ghRoot.issue.title), comment);
    }
}
