package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Head;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.PullRequest;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BuilderServiceTest {

    BuilderService builderService;
    TrelloService trelloService;

    @BeforeEach
    void setUp() {
        trelloService = Mockito.mock(TrelloService.class);
        builderService = new BuilderService(trelloService);
    }

    @Test
    @DisplayName("when using invalid trello key and token, trello request fails and returns false")
    void buildOpenPullRequest() {
        Head head = new Head();
        head.ref = "testBranch";
        PullRequest pullRequest = new PullRequest();
        pullRequest.head = head;
        pullRequest.html_url = "testURL";
        Root payload = new Root();
        payload.pull_request = pullRequest;
        String trelloKey = "invalidKey";
        String token = "invalidToken";

        assertEquals(
                Arrays.asList(false,false),
                builderService.buildOpenPullRequest(
                        payload,
                        trelloKey,
                        token
                )
        );
    }

    @Test
    void buildMergePullRequest() {
    }

    @Test
    void buildReview() {
    }

    @Test
    void buildComment() {
    }

    @Test
    void buildClosePullRequest() {
    }
}