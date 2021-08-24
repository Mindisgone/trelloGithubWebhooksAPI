package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.PullRequest;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuilderService {

  private final TrelloService trelloService;

  @Autowired
  public BuilderService(TrelloService trelloService) {
    this.trelloService = trelloService;
  }

  private void checkNull(
    TrelloService trelloService,
    Root payload,
    String trelloKey,
    String token
  ) {
    if (trelloService == null) {
      throw new NullPointerException("missing trello service");
    }

    if (payload == null) {
      throw new NullPointerException("missing payload");
    }

    if (trelloKey == null || trelloKey.equals("")) {
      throw new NullPointerException("missing key");
    }

    if (token == null || token.equals("")) {
      throw new NullPointerException("missing token");
    }
  }

  private void checkPullRequestNull(PullRequest pullRequest) {
    if (pullRequest == null) {
      throw new NullPointerException(
        "payload is missing pull_request property"
      );
    }

    if (pullRequest.head == null) {
      throw new NullPointerException(
        "payload is missing pull_request.head property"
      );
    }

    if (pullRequest.head.ref == null || pullRequest.head.ref.equals("")) {
      throw new NullPointerException(
        "payload is missing pull_request.head.ref property"
      );
    }

    if (pullRequest.html_url == null || pullRequest.html_url.equals("")) {
      throw new NullPointerException(
        "payload is missing pull_request.html_url property"
      );
    }
  }

  public List<Boolean> buildOpenPullRequest(
    Root payload,
    String trelloKey,
    String token
  ) {
    List<Boolean> responses = new ArrayList<>();
    Boolean attachResponse;
    Boolean commentResponse;

    checkNull(trelloService, payload, trelloKey, token);
    checkPullRequestNull(payload.pull_request);

    String comment = "Opened PR " + payload.pull_request.html_url;

    attachResponse =
      trelloService.addCardAttachment(
        trelloService.getCardId(
          payload.pull_request.head.ref,
          trelloKey,
          token
        ),
        payload.pull_request.html_url,
        trelloKey,
        token
      );

    if (attachResponse) {
      responses.add(0, true);
    }

    if (!attachResponse) {
      responses.add(0, false);
    }

    commentResponse =
      trelloService.addCardComment(
        trelloService.getCardId(
          payload.pull_request.head.ref,
          trelloKey,
          token
        ),
        comment,
        trelloKey,
        token
      );

    if (commentResponse) {
      responses.add(1, true);
    }

    if (!commentResponse) {
      responses.add(1, false);
    }

    return responses;
  }

  public Boolean buildMergePullRequest(
    Root payload,
    String trelloKey,
    String token
  ) {
    checkNull(trelloService, payload, trelloKey, token);
    checkPullRequestNull(payload.pull_request);

    if (payload.pull_request.merged_by == null) {
      throw new NullPointerException("missing merged by");
    }

    if (
      payload.pull_request.merged_by.login == null ||
      payload.pull_request.merged_by.login.equals("")
    ) {
      throw new NullPointerException("missing login");
    }

    String comment =
      "Merged PR " +
      payload.pull_request.html_url +
      " from " +
      payload.pull_request.merged_by.login;

    return trelloService.addCardComment(
      trelloService.getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  public Boolean buildReview(Root payload, String trelloKey, String token) {
    checkNull(trelloService, payload, trelloKey, token);
    checkPullRequestNull(payload.pull_request);

    if (payload.review == null) {
      throw new NullPointerException("missing review");
    }

    if (payload.review.user == null) {
      throw new NullPointerException("missing user");
    }

    if (payload.review.body == null || payload.review.body.equals("")) {
      throw new NullPointerException("missing body");
    }

    if (payload.review.html_url == null || payload.review.html_url.equals("")) {
      throw new NullPointerException("missing review url");
    }

    if (
      payload.review.user.login == null || payload.review.user.login.equals("")
    ) {
      throw new NullPointerException("missing login");
    }

    String comment =
      payload.review.body +
      " by Github user " +
      payload.review.user.login +
      " reviewed on " +
      payload.pull_request.html_url +
      ", review link -> " +
      payload.review.html_url;

    return trelloService.addCardComment(
      trelloService.getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  public boolean buildComment(Root payload, String trelloKey, String token) {
    checkNull(trelloService, payload, trelloKey, token);

    if (payload.comment == null) {
      throw new NullPointerException("missing comment");
    }

    if (payload.comment.user == null) {
      throw new NullPointerException("missing user");
    }

    if (
      payload.comment.user.login == null ||
      payload.comment.user.login.equals("")
    ) {
      throw new NullPointerException("missing login");
    }

    if (payload.comment.body == null || payload.comment.body.equals("")) {
      throw new NullPointerException("missing body");
    }

    if (
      payload.comment.html_url == null || payload.comment.html_url.equals("")
    ) {
      throw new NullPointerException("missing comment url");
    }

    if (payload.issue == null) {
      throw new NullPointerException("missing issue");
    }

    if (payload.issue.html_url == null || payload.issue.html_url.equals("")) {
      throw new NullPointerException("missing issue url");
    }

    if (payload.issue.title == null || payload.issue.title.equals("")) {
      throw new NullPointerException("missing issue title");
    }

    String comment =
      "Github User " +
      payload.comment.user.login +
      " commented " +
      payload.comment.body +
      " on " +
      payload.issue.html_url +
      ", comment link -> " +
      payload.comment.html_url;

    return trelloService.addCardComment(
      trelloService.getCardId(payload.issue.title, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  public Boolean buildClosePullRequest(
    Root payload,
    String trelloKey,
    String token
  ) {
    checkNull(trelloService, payload, trelloKey, token);
    checkPullRequestNull(payload.pull_request);

    if (payload.pull_request.user == null) {
      throw new NullPointerException("missing user");
    }

    if (
      payload.pull_request.user.login == null ||
      payload.pull_request.user.login.equals("")
    ) {
      throw new NullPointerException("missing login");
    }

    String comment =
      "Closed PR " +
      payload.pull_request.html_url +
      " was not merged, from " +
      payload.pull_request.user.login;

    return trelloService.addCardComment(
      trelloService.getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }
}
