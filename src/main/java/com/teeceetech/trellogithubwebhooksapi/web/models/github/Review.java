package com.teeceetech.trellogithubwebhooksapi.web.models.github;

import java.util.Date;

public class Review {

  public int id;
  public String node_id;
  public User user;
  public String body;
  public String commit_id;
  public Date submitted_at;
  public String state;
  public String html_url;
  public String pull_request_url;
  public String author_association;
  public Links _links;

  public Review() {}

  public int getId() {
    return id;
  }

  public String getNode_id() {
    return node_id;
  }

  public User getUser() {
    return user;
  }

  public String getBody() {
    return body;
  }

  public String getCommit_id() {
    return commit_id;
  }

  public Date getSubmitted_at() {
    return submitted_at;
  }

  public String getState() {
    return state;
  }

  public String getHtml_url() {
    return html_url;
  }

  public String getPull_request_url() {
    return pull_request_url;
  }

  public String getAuthor_association() {
    return author_association;
  }

  public Links get_links() {
    return _links;
  }
}
