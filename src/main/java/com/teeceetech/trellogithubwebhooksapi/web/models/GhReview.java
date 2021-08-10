package com.teeceetech.trellogithubwebhooksapi.web.models;

import java.util.Date;

public class GhReview {
    public int id;
    public String node_id;
    public GhUser user;
    public String body;
    public String commit_id;
    public Date submitted_at;
    public String state;
    public String html_url;
    public String pull_request_url;
    public String author_association;
    public GhLinks _links;

    public GhReview() {
    }

    public int getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public GhUser getUser() {
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

    public GhLinks get_links() {
        return _links;
    }
}
