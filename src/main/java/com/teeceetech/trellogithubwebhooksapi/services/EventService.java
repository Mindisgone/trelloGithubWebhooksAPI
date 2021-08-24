package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  static final Logger logger = LoggerFactory.getLogger(EventService.class);
  private final BuilderService builderService;

  @Autowired
  public EventService(BuilderService builderService) {
    this.builderService = builderService;
  }

  private void checkNull(
    BuilderService builderService,
    Root payload,
    String trelloKey,
    String token,
    String event
  ) {
    if (trelloKey == null || trelloKey.equals("")) {
      throw new NullPointerException("missing key");
    }

    if (token == null || token.equals("")) {
      throw new NullPointerException("missing token");
    }

    if (event == null || event.equals("")) {
      throw new NullPointerException("missing event");
    }

    if (builderService == null) {
      throw new NullPointerException("missing builder service");
    }

    if (payload == null) {
      throw new NullPointerException("missing payload");
    }

    if (payload.action == null || payload.action.equals("")) {
      throw new NullPointerException("missing action");
    }
  }

  public List<Boolean> receiveEvent(
    Root payload,
    String trelloKey,
    String token,
    String event
  ) {
    checkNull(builderService, payload, trelloKey, token, event);
    List<Boolean> responses = new ArrayList<>();

    if (event.equals("pull_request") && payload.getAction().equals("opened")) {
      return builderService.buildOpenPullRequest(payload, trelloKey, token);
    }

    if (
      event.equals("pull_request") &&
      payload.getAction().equals("closed") &&
      payload.pull_request != null &&
      payload.pull_request.merged
    ) {
      responses.add(
        builderService.buildMergePullRequest(payload, trelloKey, token)
      );

      return responses;
    }

    if (
      event.equals("issue_comment") && payload.getAction().equals("created")
    ) {
      responses.add(builderService.buildComment(payload, trelloKey, token));

      // PR title needs to be the same as branch name and trello card
      return responses;
    }

    if (
      event.equals("pull_request_review") &&
      payload.getAction().equals("submitted")
    ) {
      responses.add(builderService.buildReview(payload, trelloKey, token));

      return responses;
    }

    if (
      event.equals("pull_request") &&
      payload.getAction().equals("closed") &&
      payload.pull_request != null &&
      !payload.pull_request.merged
    ) {
      responses.add(
        builderService.buildClosePullRequest(payload, trelloKey, token)
      );

      return responses;
    }

    responses.add(false);
    return responses;
  }

  public void handleEvent(List<Boolean> responses) {
    if (responses == null || responses.isEmpty()) {
      throw new NullPointerException("missing response");
    }

    for (Boolean response : responses) {
      if (response) {
        logger.info("successfully pushed to Trello card");
      } else {
        logger.info("failed to push to Trello card");
      }
    }
  }
}
