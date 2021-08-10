package com.teeceetech.trellogithubwebhooksapi.web.models;

public class GhPrRoot {
    public String action;
    public int number;
    public GhPullRequest pull_request;
    public GhRepository repository;
    public GhSender sender;

    public GhPrRoot() {
    }

    public String getAction() {
        return action;
    }

    public int getNumber() {
        return number;
    }

    public GhPullRequest getPull_request() {
        return pull_request;
    }

    public GhRepository getRepository() {
        return repository;
    }

    public GhSender getSender() {
        return sender;
    }
}
