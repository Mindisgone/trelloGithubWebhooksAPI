package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import java.util.ArrayList;
import java.util.Arrays;
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
      throw new NullPointerException("Missing payload");
    }

    if (trelloKey == null || trelloKey.equals("")) {
      throw new NullPointerException("Missing key");
    }

    if (token == null || token.equals("")) {
      throw new NullPointerException("Missing token");
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

    if (payload.pull_request == null) {
      throw new NullPointerException(
        "payload is missing pull_request property"
      );
    }

    if (payload.pull_request.head == null) {
      throw new NullPointerException(
        "payload is missing pull_request.head property"
      );
    }

    if (
      payload.pull_request.head.ref == null ||
      payload.pull_request.head.ref.equals("")
    ) {
      throw new NullPointerException(
        "payload is missing pull_request.head.ref property"
      );
    }

    if (
      payload.pull_request.html_url == null ||
      payload.pull_request.html_url.equals("")
    ) {
      throw new NullPointerException(
        "payload is missing pull_request.html_url property"
      );
    }

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

    if (
      payload.pull_request.html_url == null ||
      payload.pull_request.html_url.equals("")
    ) {
      throw new NullPointerException(
        "payload is missing pull_request.html_url property"
      );
    }

    if (
      payload.pull_request.merged_by.login == null ||
      payload.pull_request.merged_by.login.equals("")
    ) {
      throw new NullPointerException(
        "payload is missing pull_request.html_url property"
      );
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
