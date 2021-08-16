package com.teeceetech.trellogithubwebhooksapi.web.models.github;

import java.util.Date;

public class Comment {

  public String url;
  public String html_url;
  public String issue_url;
  public int id;
  public String node_id;
  public User user;
  public Date created_at;
  public Date updated_at;
  public String author_association;
  public String body;
  public Object performed_via_github_app;

  public Comment() {}

  public String getUrl() {
    return url;
  }

  public String getHtml_url() {
    return html_url;
  }

  public String getIssue_url() {
    return issue_url;
  }

  public int getId() {
    return id;
  }

  public String getNode_id() {
    return node_id;
  }

  public User getUser() {
    return user;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public Date getUpdated_at() {
    return updated_at;
  }

  public String getAuthor_association() {
    return author_association;
  }

  public String getBody() {
    return body;
  }

  public Object getPerformed_via_github_app() {
    return performed_via_github_app;
  }
}
