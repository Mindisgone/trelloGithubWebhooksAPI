package com.teeceetech.trellogithubwebhooksapi.web.models.github;

public class Root {

  public String action;
  // used only for pull request events
  public int number;
  // used only for review events
  public Review review;
  // used only for comment events
  public Issue issue;
  // used only for comment events
  public Comment comment;
  // not used for comment events
  public PullRequest pull_request;
  public Repository repository;
  public Sender sender;

  public Root() {}

  public String getAction() {
    return action;
  }

  public int getNumber() {
    return number;
  }

  public Review getReview() {
    return review;
  }

  public Issue getIssue() {
    return issue;
  }

  public Comment getComment() {
    return comment;
  }

  public PullRequest getPull_request() {
    return pull_request;
  }

  public Repository getRepository() {
    return repository;
  }

  public Sender getSender() {
    return sender;
  }
}
