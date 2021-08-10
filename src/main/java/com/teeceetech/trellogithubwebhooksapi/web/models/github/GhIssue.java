package com.teeceetech.trellogithubwebhooksapi.web.models.github;

import java.util.Date;
import java.util.List;

public class GhIssue {
    public String url;
    public String repository_url;
    public String labels_url;
    public String comments_url;
    public String events_url;
    public String html_url;
    public int id;
    public String node_id;
    public int number;
    public String title;
    public GhUser user;
    public List<Object> labels;
    public String state;
    public boolean locked;
    public GhAssignee assignee;
    public List<GhAssignee> assignees;
    public Object milestone;
    public int comments;
    public Date created_at;
    public Date updated_at;
    public Object closed_at;
    public String author_association;
    public Object active_lock_reason;
    public GhPullRequest pull_request;
    public String body;
    public Object performed_via_github_app;

    public GhIssue() {
    }

    public String getUrl() {
        return url;
    }

    public String getRepository_url() {
        return repository_url;
    }

    public String getLabels_url() {
        return labels_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public int getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public GhUser getUser() {
        return user;
    }

    public List<Object> getLabels() {
        return labels;
    }

    public String getState() {
        return state;
    }

    public boolean isLocked() {
        return locked;
    }

    public GhAssignee getAssignee() {
        return assignee;
    }

    public List<GhAssignee> getAssignees() {
        return assignees;
    }

    public Object getMilestone() {
        return milestone;
    }

    public int getComments() {
        return comments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public Object getClosed_at() {
        return closed_at;
    }

    public String getAuthor_association() {
        return author_association;
    }

    public Object getActive_lock_reason() {
        return active_lock_reason;
    }

    public GhPullRequest getPull_request() {
        return pull_request;
    }

    public String getBody() {
        return body;
    }

    public Object getPerformed_via_github_app() {
        return performed_via_github_app;
    }
}
