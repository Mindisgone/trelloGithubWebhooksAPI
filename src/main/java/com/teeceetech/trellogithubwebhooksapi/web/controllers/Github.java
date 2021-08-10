package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.GhPrRoot;
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
    public void receiveGithubMessage(@RequestBody GhPrRoot message){
        if (message.action != null && message.getAction().equals("opened")){
            logger.info("GH message = " + message);
            logger.info("PR OPENED, url = " + message.pull_request.url);
        }
    }
}
