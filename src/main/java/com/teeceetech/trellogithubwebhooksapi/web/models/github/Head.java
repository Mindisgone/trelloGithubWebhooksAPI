package com.teeceetech.trellogithubwebhooksapi.web.models.github;

public class Head {

  public String label;
  public String ref;
  public String sha;
  public User user;
  public Repo repo;

  public Head() {}

  public String getLabel() {
    return label;
  }

  public String getRef() {
    return ref;
  }

  public String getSha() {
    return sha;
  }

  public User getUser() {
    return user;
  }

  public Repo getRepo() {
    return repo;
  }
}
