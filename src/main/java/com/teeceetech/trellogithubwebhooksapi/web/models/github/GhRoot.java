package com.teeceetech.trellogithubwebhooksapi.web.models.github;

public class GhRoot {
    public String action;
    // used only for pull request events
    public int number;
    // used only for review events
    public GhReview review;
    public GhPullRequest pull_request;
    public GhRepository repository;
    public GhSender sender;

    public GhRoot() {
    }

    public String getAction() {
        return action;
    }

    public int getNumber() {
        return number;
    }

    public GhReview getReview() {
        return review;
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
