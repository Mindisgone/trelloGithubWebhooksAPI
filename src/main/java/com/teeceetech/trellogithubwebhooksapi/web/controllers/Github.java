package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.GhRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Github {
    Logger logger = LoggerFactory.getLogger(Github.class);

    @Autowired
    public Github(){}

    @RequestMapping(value = "/api/github", method = RequestMethod.POST)
    public void receiveGithubMessage(@RequestBody GhRoot message){
        if (message.action != null && message.getAction().equals("opened")){
            logger.info("PR OPENED, url = " + message.pull_request.url);
            logger.info("PR OPENED, id = " + message.pull_request.number);
            logger.info("PR OPENED, author is = " + message.pull_request.assignee.login);
            logger.info("PR OPENED, branch is = " + message.pull_request.head.ref);
        }

        if (message.action != null && message.getAction().equals("closed") && message.pull_request.merged){
            logger.info("PR CLOSED, url = " + message.pull_request.url);
            logger.info("PR CLOSED, id = " + message.pull_request.number);
            logger.info("PR CLOSED, author is = " + message.pull_request.merged_by.login);
            logger.info("PR CLOSED, branch is = " + message.pull_request.head.ref);
        }
    }
}
