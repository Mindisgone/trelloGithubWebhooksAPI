package com.teeceetech.trellogithubwebhooksapi.services;

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

  public List<Boolean> buildOpenPullRequest(
    Root payload,
    String trelloKey,
    String token
  ) {
    List<Boolean> responses = new ArrayList<>();
    String comment = "Opened PR " + payload.pull_request.html_url;

    responses.add(
      0,
      trelloService.addCardAttachment(
        trelloService.getCardId(
          payload.pull_request.head.ref,
          trelloKey,
          token
        ),
        payload.pull_request.html_url,
        trelloKey,
        token
      )
    );

    responses.add(
      1,
      trelloService.addCardComment(
        trelloService.getCardId(
          payload.pull_request.head.ref,
          trelloKey,
          token
        ),
        comment,
        trelloKey,
        token
      )
    );

    return responses;
  }

  public Boolean buildMergePullRequest(
    Root payload,
    String trelloKey,
    String token
  ) {
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
