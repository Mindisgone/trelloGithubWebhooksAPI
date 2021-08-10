package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Trello {
    Logger logger = LoggerFactory.getLogger(Github.class);

    @Autowired
    public Trello(){}

}
