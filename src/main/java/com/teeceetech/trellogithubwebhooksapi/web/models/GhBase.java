package com.teeceetech.trellogithubwebhooksapi.web.models;

public class GhBase {
    public String label;
    public String ref;
    public String sha;
    public GhUser user;
    public GhRepo repo;

    public GhBase() {
    }

    public String getLabel() {
        return label;
    }

    public String getRef() {
        return ref;
    }

    public String getSha() {
        return sha;
    }

    public GhUser getUser() {
        return user;
    }

    public GhRepo getRepo() {
        return repo;
    }
}
