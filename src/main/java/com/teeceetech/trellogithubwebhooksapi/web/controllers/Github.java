package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Github {
    Logger logger = LoggerFactory.getLogger(Github.class);

    @Autowired
    public Github(){}

    @RequestMapping(value = "/api/github", method = RequestMethod.POST)
    public void receiveGithubMessage(@RequestBody Map<String, Object> message){
        logger.info("GH message = " + message.toString());

        if (message.get("action").toString().equals("opened")){
            logger.info("PR OPENED, msg = " + message);
        }
    }
}
