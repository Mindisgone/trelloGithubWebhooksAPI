package com.teeceetech.trellogithubwebhooksapi.web.models.github;

public class GhLinks {
    public GhSelf self;
    public GhHtml html;
    public GhIssue issue;
    public GhComments comments;
    public GhComments review_comments;
    public GhReviewComment review_comment;
    public GhCommits commits;
    public GhStatuses statuses;

    public GhLinks() {
    }

    public GhSelf getSelf() {
        return self;
    }

    public GhHtml getHtml() {
        return html;
    }

    public GhIssue getIssue() {
        return issue;
    }

    public GhComments getComments() {
        return comments;
    }

    public GhComments getReview_comments() {
        return review_comments;
    }

    public GhReviewComment getReview_comment() {
        return review_comment;
    }

    public GhCommits getCommits() {
        return commits;
    }

    public GhStatuses getStatuses() {
        return statuses;
    }
}
