package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final BuilderService builderService;

    @Autowired
    public EventService(BuilderService builderService) {
        this.builderService = builderService;
    }

    public List<Boolean> receiveEvent(Root payload,
                             String trelloKey,
                             String token,
                             String event
    ){
        if (
                trelloKey != null &&
                        token != null &&
                        payload.action != null &&
                        event != null
        ) {
            if (
                    event.equals("pull_request") && payload.getAction().equals("opened")
            ) {
                return builderService.buildOpenPullRequest(payload, trelloKey, token);
            }

            if (
                    event.equals("pull_request") &&
                            payload.getAction().equals("closed") &&
                            payload.pull_request.merged
            ) {
                return Arrays.asList(builderService.buildMergePullRequest(payload, trelloKey, token));
            }

            if (
                    event.equals("issue_comment") &&
                            payload.getAction().equals("created") &&
                            payload.comment != null &&
                            payload.issue != null
            ) {
                // PR title needs to be the same as branch name and trello card
                return Arrays.asList(builderService.buildComment(payload, trelloKey, token));
            }

            if (
                    event.equals("pull_request_review") &&
                            payload.getAction().equals("submitted")
            ) {
                return Arrays.asList(builderService.buildReview(payload, trelloKey, token));
            }

            if (
                    event.equals("pull_request") &&
                            payload.getAction().equals("closed") &&
                            !payload.pull_request.merged
            ) {
                return Arrays.asList(builderService.buildPrClosedComment(payload, trelloKey, token));
            }
        } else {
            logger.warn(
                    "Bad request, header: {}, payload exists: {}, trello key exists: {}, trello token exists: {}",
                    event,
                    payload != null,
                    trelloKey != null,
                    token != null
            );

        }
        return Arrays.asList(false);
    }

    public void handleEvent(List<Boolean> responses){
        if (responses.size() > 0){
            for (Boolean response : responses){
                if (response){
                    logger.info("successfully pushed to Trello card");
                } else {
                    logger.info("failed to push to Trello card");
                }
            }
        } else {
            logger.info("did not receive any responses");
        }
    }
}
