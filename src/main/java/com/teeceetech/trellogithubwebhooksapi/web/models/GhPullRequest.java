package com.teeceetech.trellogithubwebhooksapi.web.models;

public class GhPullRequest {
    public String url;
    public String html_url;
    public String diff_url;
    public String patch_url;

    public GhPullRequest() {
    }

    public String getUrl() {
        return url;
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
}
