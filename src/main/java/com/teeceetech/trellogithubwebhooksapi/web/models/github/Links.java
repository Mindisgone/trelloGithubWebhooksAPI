package com.teeceetech.trellogithubwebhooksapi.web.models.github;

public class Links {

  public Self self;
  public Html html;
  public Issue issue;
  public Comments comments;
  public Comments review_comments;
  public ReviewComment review_comment;
  public Commits commits;
  public Statuses statuses;

  public Links() {}

  public Self getSelf() {
    return self;
  }

  public Html getHtml() {
    return html;
  }

  public Issue getIssue() {
    return issue;
  }

  public Comments getComments() {
    return comments;
  }

  public Comments getReview_comments() {
    return review_comments;
  }

  public ReviewComment getReview_comment() {
    return review_comment;
  }

  public Commits getCommits() {
    return commits;
  }

  public Statuses getStatuses() {
    return statuses;
  }
}
