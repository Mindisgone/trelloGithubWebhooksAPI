package com.teeceetech.trellogithubwebhooksapi.web.models;

import java.util.Date;
import java.util.List;

public class GhPullRequest {
    public String url;
    public int id;
    public String node_id;
    public String html_url;
    public String diff_url;
    public String patch_url;
    public String issue_url;
    public int number;
    public String state;
    public boolean locked;
    public String title;
    public GhUser user;
    public String body;
    public Date created_at;
    public Date updated_at;
    public Date closed_at;
    public Date merged_at;
    public String merge_commit_sha;
    public GhAssignee assignee;
    public List<GhAssignee> assignees;
    public List<Object> requested_reviewers;
    public List<Object> requested_teams;
    public List<Object> labels;
    public Object milestone;
    public boolean draft;
    public String commits_url;
    public String review_comments_url;
    public String review_comment_url;
    public String comments_url;
    public String statuses_url;
    public GhHead head;
    public GhBase base;
    public GhLinks _links;
    public String author_association;
    public Object auto_merge;
    public Object active_lock_reason;
    public boolean merged;
    public Object mergeable;
    public Object rebaseable;
    public String mergeable_state;
    public GhMergedBy merged_by;
    public int comments;
    public int review_comments;
    public boolean maintainer_can_modify;
    public int commits;
    public int additions;
    public int deletions;
    public int changed_files;

    public GhPullRequest() {
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getDiff_url() {
        return diff_url;
    }

    public String getPatch_url() {
        return patch_url;
    }

    public String getIssue_url() {
        return issue_url;
    }

    public int getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getTitle() {
        return title;
    }

    public GhUser getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public Date getClosed_at() {
        return closed_at;
    }

    public Date getMerged_at() {
        return merged_at;
    }

    public String getMerge_commit_sha() {
        return merge_commit_sha;
    }

    public GhAssignee getAssignee() {
        return assignee;
    }

    public List<GhAssignee> getAssignees() {
        return assignees;
    }

    public List<Object> getRequested_reviewers() {
        return requested_reviewers;
    }

    public List<Object> getRequested_teams() {
        return requested_teams;
    }

    public List<Object> getLabels() {
        return labels;
    }

    public Object getMilestone() {
        return milestone;
    }

    public boolean isDraft() {
        return draft;
    }

    public String getCommits_url() {
        return commits_url;
    }

    public String getReview_comments_url() {
        return review_comments_url;
    }

    public String getReview_comment_url() {
        return review_comment_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public String getStatuses_url() {
        return statuses_url;
    }

    public GhHead getHead() {
        return head;
    }

    public GhBase getBase() {
        return base;
    }

    public GhLinks get_links() {
        return _links;
    }

    public String getAuthor_association() {
        return author_association;
    }

    public Object getAuto_merge() {
        return auto_merge;
    }

    public Object getActive_lock_reason() {
        return active_lock_reason;
    }

    public boolean isMerged() {
        return merged;
    }

    public Object getMergeable() {
        return mergeable;
    }

    public Object getRebaseable() {
        return rebaseable;
    }

    public String getMergeable_state() {
        return mergeable_state;
    }

    public GhMergedBy getMerged_by() {
        return merged_by;
    }

    public int getComments() {
        return comments;
    }

    public int getReview_comments() {
        return review_comments;
    }

    public boolean isMaintainer_can_modify() {
        return maintainer_can_modify;
    }

    public int getCommits() {
        return commits;
    }

    public int getAdditions() {
        return additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getChanged_files() {
        return changed_files;
    }
}
