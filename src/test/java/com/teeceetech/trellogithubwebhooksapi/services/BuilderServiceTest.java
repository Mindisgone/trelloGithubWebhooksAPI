package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

class BuilderServiceTest {

    private BuilderService builderService;
    private TrelloService trelloService;

    @BeforeEach
    void setUp() {
        trelloService = Mockito.mock(TrelloService.class);
        builderService = new BuilderService(trelloService);
    }

    @Nested
    @DisplayName("when building open pull request and given")
    class buildOpenPullRequest {
        @Test
        @DisplayName("valid query parameters - return true")
        void validQuery() {
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("testID").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardAttachment(anyString(), anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            List<Boolean> successes = builderService.buildOpenPullRequest(payload, trelloKey, token);

            assertEquals(Arrays.asList(true, true), successes);
        }

        @Test
        @DisplayName("invalid branch name - return false")
        void invalidRefQuery() {
            Head head = new Head();
            head.ref = "INVALID";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardAttachment(anyString(), anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            List<Boolean> successes = builderService.buildOpenPullRequest(payload, trelloKey, token);

            assertEquals(Arrays.asList(false, false), successes);
        }

        @Test
        @DisplayName("invalid pull request URL - return false")
        void invalidUrlQuery() {
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "INVALID";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardAttachment(anyString(), anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            List<Boolean> successes = builderService.buildOpenPullRequest(payload, trelloKey, token);

            assertEquals(Arrays.asList(false, true), successes);
        }

        @Test
        @DisplayName("invalid trello key - return false")
        void invalidKeyQuery() {
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "INVALID";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardAttachment(anyString(), anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            List<Boolean> successes = builderService.buildOpenPullRequest(payload, trelloKey, token);

            assertEquals(Arrays.asList(false, false), successes);
        }

        @Test
        @DisplayName("invalid Trello token - return false")
        void invalidTokenQuery() {
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "INVALID";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardAttachment(anyString(), anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            List<Boolean> successes = builderService.buildOpenPullRequest(payload, trelloKey, token);

            assertEquals(Arrays.asList(false, false), successes);
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void payloadNullThrowException() throws NullPointerException{
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(null, trelloKey, token));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as ref - throw exception")
        void refNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head.ref property", exception.getMessage());
        }

        @Test
        @DisplayName("null as head - throw exception")
        void headNullThrowException() throws NullPointerException{
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request URL - throw exception")
        void urlNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.html_url property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request - throw exception")
        void pullRequestNullThrowException() throws NullPointerException{
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildOpenPullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request property", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("when building merge and given")
    class buildMergePullRequest {
        @Test
        @DisplayName("valid query parameters - return true")
        void validQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("testID").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid merged by login - return false")
        void invalidLoginQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "INVALID";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid branch name - return false")
        void invalidRefQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "INVALID";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid pull request URL - return false")
        void invalidUrlQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "INVALID";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid trello key - return false")
        void invalidKeyQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "INVALID";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello token - return false")
        void invalidTokenQuery() {
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "INVALID";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildMergePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void payloadNullThrowException() throws NullPointerException{
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(null, trelloKey, token));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as ref - throw exception")
        void refNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head.ref property", exception.getMessage());
        }

        @Test
        @DisplayName("null as head - throw exception")
        void headNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request URL - throw exception")
        void urlNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.html_url property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request - throw exception")
        void pullRequestNullThrowException() throws NullPointerException{
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request property", exception.getMessage());
        }

        @Test
        @DisplayName("null as login - throw exception")
        void loginNullThrowException() throws NullPointerException{
            MergedBy mergedBy = new MergedBy();
            mergedBy.login = "";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.merged_by = mergedBy;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("missing login", exception.getMessage());
        }

        @Test
        @DisplayName("null as merged by - throw exception")
        void mergedByNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildMergePullRequest(payload, trelloKey, token));

            assertEquals("missing merged by", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("when building a review and given")
    class buildReview {
        @Test
        @DisplayName("valid query parameters - return true")
        void validQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("testID").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid merged by login - return false")
        void invalidLoginQuery() {
            User user = new User();
            user.login = "INVALID";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid review body - return false")
        void invalidBodyQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "INVALID";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid review URL - return false")
        void invalidReviewUrlQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "INVALID";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid branch name - return false")
        void invalidRefQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "html_url";
            review.user = user;
            Head head = new Head();
            head.ref = "INVALID";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid pull request URL - return false")
        void invalidUrlQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "INVALID";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello key - return false")
        void invalidKeyQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "INVALID";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello token - return false")
        void invalidTokenQuery() {
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "INVALID";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildReview(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void payloadNullThrowException() throws NullPointerException{
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(null, trelloKey, token));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as ref - throw exception")
        void refNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head.ref property", exception.getMessage());
        }

        @Test
        @DisplayName("null as head - throw exception")
        void headNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request URL - throw exception")
        void urlNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.html_url property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request - throw exception")
        void pullRequestNullThrowException() throws NullPointerException{
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("payload is missing pull_request property", exception.getMessage());
        }

        @Test
        @DisplayName("null as login - throw exception")
        void loginNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "";
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing login", exception.getMessage());
        }

        @Test
        @DisplayName("null as user - throw exception")
        void userNullThrowException() throws NullPointerException{
            Review review = new Review();
            review.body = "body";
            review.html_url = "review_url";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing user", exception.getMessage());
        }

        @Test
        @DisplayName("null as review - throw exception")
        void reviewNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing review", exception.getMessage());
        }

        @Test
        @DisplayName("null as body - throw exception")
        void bodyNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "";
            review.html_url = "review_url";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing body", exception.getMessage());
        }

        @Test
        @DisplayName("null as review URL - throw exception")
        void reviewUrlNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Review review = new Review();
            review.body = "body";
            review.html_url = "";
            review.user = user;
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            payload.review = review;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildReview(payload, trelloKey, token));

            assertEquals("missing review url", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("When building a comment and given")
    class buildComment {
        @Test
        @DisplayName("valid query parameters - return true")
        void validQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("testID").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid user login - return false")
        void invalidLoginQuery() {
            User user = new User();
            user.login = "INVALID";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid comment url - return false")
        void invalidCommentUrlQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "INVALID";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid comment body - return false")
        void invalidCommentBodyQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "INVALID";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid issue url - return false")
        void invalidIssueUrlQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "INVALID";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid issue title - return false")
        void invalidIssueTitleQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "INVALID";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello key - return false")
        void invalidKeyQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "INVALID";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello token - return false")
        void invalidTokenQuery() {
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "INVALID";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildComment(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void payloadNullThrowException() throws NullPointerException{
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(null, trelloKey, token));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as login - throw exception")
        void loginNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing login", exception.getMessage());
        }

        @Test
        @DisplayName("null as user - throw exception")
        void userNullThrowException() throws NullPointerException{
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing user", exception.getMessage());
        }

        @Test
        @DisplayName("null as comment - throw exception")
        void commentNullThrowException() throws NullPointerException{
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing comment", exception.getMessage());
        }

        @Test
        @DisplayName("null as comment URL - throw exception")
        void commentUrlNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing comment url", exception.getMessage());
        }

        @Test
        @DisplayName("null as comment body - throw exception")
        void bodyNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing body", exception.getMessage());
        }

        @Test
        @DisplayName("null as issue - throw exception")
        void issueNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Root payload = new Root();
            payload.comment = comment;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing issue", exception.getMessage());
        }

        @Test
        @DisplayName("null as issue url - throw exception")
        void issueUrlNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "";
            issue.title = "title";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing issue url", exception.getMessage());
        }

        @Test
        @DisplayName("null as issue title - throw exception")
        void issueTitleNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Comment comment = new Comment();
            comment.html_url = "comment_url";
            comment.body = "body";
            comment.user = user;
            Issue issue = new Issue();
            issue.html_url = "issue_url";
            issue.title = "";
            Root payload = new Root();
            payload.comment = comment;
            payload.issue = issue;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildComment(payload, trelloKey, token));

            assertEquals("missing issue title", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("when building a pull request close")
    class buildClosePullRequest {
        @Test
        @DisplayName("valid query parameters - return true")
        void validQuery() {
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("testID").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(true).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(true, success);
        }

        @Test
        @DisplayName("invalid user login - return false")
        void invalidLoginQuery() {
            User user = new User();
            user.login = "INVALID";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid head ref - return false")
        void invalidRefQuery() {
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "INVALID";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid pull request URL - return false")
        void invalidUrlQuery() {
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "INVALID";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            doReturn("branch_name").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello key - return false")
        void invalidKeyQuery() {
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "INVALID";
            String token = "token";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("invalid Trello token - return false")
        void invalidTokenQuery() {
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "INVALID";

            doReturn("").when(trelloService).getCardId(anyString(), anyString(), anyString());
            doReturn(false).when(trelloService).addCardComment(anyString(), anyString(), anyString(), anyString());

            Boolean success = builderService.buildClosePullRequest(payload, trelloKey, token);

            assertEquals(false, success);
        }

        @Test
        @DisplayName("null as key - throw exception")
        void keyNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("missing key", exception.getMessage());
        }

        @Test
        @DisplayName("null as token - throw exception")
        void tokenNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("missing token", exception.getMessage());
        }

        @Test
        @DisplayName("null as payload - throw exception")
        void payloadNullThrowException() throws NullPointerException{
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(null, trelloKey, token));

            assertEquals("missing payload", exception.getMessage());
        }

        @Test
        @DisplayName("null as ref - throw exception")
        void refNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head.ref property", exception.getMessage());
        }

        @Test
        @DisplayName("null as head - throw exception")
        void headNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.head property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request URL - throw exception")
        void urlNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "login";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request.html_url property", exception.getMessage());
        }

        @Test
        @DisplayName("null as pull request - throw exception")
        void pullRequestNullThrowException() throws NullPointerException{
            Root payload = new Root();
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("payload is missing pull_request property", exception.getMessage());
        }

        @Test
        @DisplayName("null as login - throw exception")
        void loginNullThrowException() throws NullPointerException{
            User user = new User();
            user.login = "";
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            pullRequest.user = user;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("missing login", exception.getMessage());
        }

        @Test
        @DisplayName("null as user - throw exception")
        void userNullThrowException() throws NullPointerException{
            Head head = new Head();
            head.ref = "branch_name";
            PullRequest pullRequest = new PullRequest();
            pullRequest.html_url = "html_url";
            pullRequest.head = head;
            Root payload = new Root();
            payload.pull_request = pullRequest;
            String trelloKey = "key";
            String token = "token";

            NullPointerException exception = assertThrows(NullPointerException.class, () ->
                    builderService.buildClosePullRequest(payload, trelloKey, token));

            assertEquals("missing user", exception.getMessage());
        }
    }
}