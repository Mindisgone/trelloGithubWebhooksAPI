package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.GhRoot;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class Github {
    Logger logger = LoggerFactory.getLogger(Github.class);
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public Github(){}

    @RequestMapping(value = "/api/github/{trelloKey}/{token}", method = RequestMethod.POST)
    public void receiveGithubMessage(@RequestBody GhRoot message, @PathVariable String trelloKey, @PathVariable String token){
        if (trelloKey != null && token != null){
            if (message.action != null && message.getAction().equals("opened")){
                buildPrOpenComment(message, trelloKey, token);
            }

            if (message.action != null && message.getAction().equals("closed") && message.pull_request.merged){
                buildPrMergedComment(message, trelloKey, token);
            }

            if (message.action != null && message.getAction().equals("created")){
                // PR title needs to be the same as branch name and trello card
                buildPrComment(message, trelloKey, token);
            }
        }
    }

    private String getCardId(String name, String trelloKey, String token) {
        String cardId = "";
        String url = "https://api.trello.com/1/search?modelTypes=cards&query=" +
                name + "&key=" + trelloKey + "&token=" + token;

        Root root = restTemplate.getForObject(url, Root.class);

        if (root != null && root.cards.size() == 1){
            cardId = root.cards.get(0).id;
        } else {
            logger.error("search produced no results");
        }

        return cardId;
    }

    private void addCardComment(String ID, String text, String trelloKey, String token) {
        String url = "https://api.trello.com/1/cards/" +
                ID + "/actions/comments?key=" + trelloKey + "&token=" + token;
        Term term = new Term();
        term.setText(text);

        logger.info("GITHUB CONTROLLER: adding comment " + text + " to card " + ID);
        restTemplate.postForLocation(url, term);
    }

    private void buildPrOpenComment(GhRoot ghRoot, String trelloKey, String token) {
        String comment = "Opened PR " + ghRoot.pull_request.number + " " + ghRoot.pull_request.url;

        addCardComment(getCardId(ghRoot.pull_request.head.ref, trelloKey, token), comment, trelloKey, token);
    }

    private void buildPrMergedComment(GhRoot ghRoot, String trelloKey, String token) {
        String comment = "Merged PR " + ghRoot.pull_request.number + " from " +
                ghRoot.pull_request.merged_by.login + " " + ghRoot.pull_request.url;

        addCardComment(getCardId(ghRoot.pull_request.head.ref, trelloKey, token), comment, trelloKey, token);
    }

    private void buildPrComment(GhRoot ghRoot, String trelloKey, String token) {
        String comment = "{Github User " + ghRoot.comment.user.login + " commented " +
                ghRoot.comment.body + " on PR " + ghRoot.issue.number + " " + ghRoot.comment.html_url + "}";

        addCardComment(getCardId(ghRoot.issue.title, trelloKey, token), comment, trelloKey, token);
    }
}
