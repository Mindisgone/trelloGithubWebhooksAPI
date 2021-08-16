package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Attachment;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Card;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class Github {

  @Autowired
  public Github() {}

  @RequestMapping(
    value = "/api/github/{trelloKey}/{token}",
    method = RequestMethod.POST
  )
  public void receiveGithubMessage(
    @RequestBody Root payload,
    @PathVariable String trelloKey,
    @PathVariable String token,
    @RequestHeader("X-Github-Event") String event
  ) {
    if (
      trelloKey != null &&
      token != null &&
      payload.action != null &&
      event != null
    ) {
      if (
        event.equals("pull_request") && payload.getAction().equals("opened")
      ) {
        buildPrOpenComment(payload, trelloKey, token);
      }

      if (
        event.equals("pull_request") &&
        payload.getAction().equals("closed") &&
        payload.pull_request.merged
      ) {
        buildPrMergedComment(payload, trelloKey, token);
      }

      if (
        event.equals("issue_comment") &&
        payload.getAction().equals("created") &&
        payload.comment != null &&
        payload.issue != null
      ) {
        // PR title needs to be the same as branch name and trello card
        buildPrComment(payload, trelloKey, token);
      }

      if (
        event.equals("pull_request_review") &&
        payload.getAction().equals("submitted")
      ) {
        buildPrReviewComment(payload, trelloKey, token);
      }

      if (
        event.equals("pull_request") &&
        payload.getAction().equals("closed") &&
        !payload.pull_request.merged
      ) {
        buildPrClosedComment(payload, trelloKey, token);
      }
    }
  }

  private String getCardId(String name, String trelloKey, String token) {
    RestTemplate restTemplate = new RestTemplate();
    String cardId = "";
    String url =
      "https://api.trello.com/1/search?modelTypes=cards&query=" +
      name +
      "&key=" +
      trelloKey +
      "&token=" +
      token;

    com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root root = restTemplate.getForObject(url, com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root.class);

    if (root != null) {
      for (Card card : root.cards) {
        if (card.name.equals(name)) {
          cardId = card.id;
        }
      }
    }

    return cardId;
  }

  private void addCardAttachment(
    String ID,
    String attachmentURL,
    String trelloKey,
    String token
  ) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
      "https://api.trello.com/1/cards/" +
      ID +
      "/attachments?key=" +
      trelloKey +
      "&token=" +
      token;
    Attachment attachment = new Attachment();
    attachment.setUrl(attachmentURL);

    if (ID.length() > 0) {
      restTemplate.postForLocation(url, attachment);
    }
  }

  private void addCardComment(
    String ID,
    String text,
    String trelloKey,
    String token
  ) {
    RestTemplate restTemplate = new RestTemplate();
    String url =
      "https://api.trello.com/1/cards/" +
      ID +
      "/actions/comments?key=" +
      trelloKey +
      "&token=" +
      token;
    Term term = new Term();
    term.setText(text);

    if (ID.length() > 0) {
      restTemplate.postForLocation(url, term);
    }
  }

  private void buildPrOpenComment(
    Root payload,
    String trelloKey,
    String token
  ) {
    String comment = "Opened PR " + payload.pull_request.html_url;

    addCardAttachment(
      getCardId(payload.pull_request.head.ref, trelloKey, token),
      payload.pull_request.html_url,
      trelloKey,
      token
    );
    addCardComment(
      getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  private void buildPrMergedComment(
    Root payload,
    String trelloKey,
    String token
  ) {
    String comment =
      "Merged PR " +
      payload.pull_request.html_url +
      " from " +
      payload.pull_request.merged_by.login;

    addCardComment(
      getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  private void buildPrReviewComment(
    Root payload,
    String trelloKey,
    String token
  ) {
    String comment =
      payload.review.body +
      " by Github user " +
      payload.review.user.login +
      " reviewed on " +
      payload.pull_request.html_url +
      ", review link -> " +
      payload.review.html_url;

    addCardComment(
      getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  private void buildPrComment(Root payload, String trelloKey, String token) {
    String comment =
      "Github User " +
      payload.comment.user.login +
      " commented " +
      payload.comment.body +
      " on " +
      payload.issue.html_url +
      ", comment link -> " +
      payload.comment.html_url;

    addCardComment(
      getCardId(payload.issue.title, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }

  private void buildPrClosedComment(
    Root payload,
    String trelloKey,
    String token
  ) {
    String comment =
      "Closed PR " +
      payload.pull_request.html_url +
      " was not merged, from " +
      payload.pull_request.merged_by.login;

    addCardComment(
      getCardId(payload.pull_request.head.ref, trelloKey, token),
      comment,
      trelloKey,
      token
    );
  }
}
