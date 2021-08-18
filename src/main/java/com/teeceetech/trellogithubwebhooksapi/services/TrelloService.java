package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.handlers.RestTemplateErrorHandler;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.Attachment;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Card;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrelloService {

  static final Logger logger = LoggerFactory.getLogger(TrelloService.class);

  @Autowired
  public TrelloService() {}

  public String getCardId(String name, String trelloKey, String token) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());

    String cardId = "";
    String url =
      "https://api.trello.com/1/search?modelTypes=cards&query=" +
      name +
      "&key=" +
      trelloKey +
      "&token=" +
      token;

    com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root root = restTemplate.getForObject(
      url,
      com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root.class
    );

    if (root != null) {
      for (Card card : root.cards) {
        if (card.name.equals(name)) {
          cardId = card.id;
          logger.info("Trello card found");
        }
      }
    } else {
      logger.warn("Cannot retrieve Trello card, response does not exist");
    }

    return cardId;
  }

  public Boolean addCardAttachment(
    String ID,
    String attachmentURL,
    String trelloKey,
    String token
  ) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
      String response = restTemplate
        .postForEntity(url, attachment, String.class)
        .getBody();

      if (response != null && response.equals("Success")) {
        return true;
      } else {
        logger.warn("Post to Trello API failed -> " + response);
        return false;
      }
    } else {
      logger.warn("Trello card ID invalid, cannot attach link -> " + ID);
      return false;
    }
  }

  public Boolean addCardComment(
    String ID,
    String text,
    String trelloKey,
    String token
  ) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());

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
      String response = restTemplate
        .postForEntity(url, term, String.class)
        .getBody();

      if (response != null && response.equals("Success")) {
        logger.info("Comment added to Trello card");
        return true;
      } else {
        logger.warn("Post to Trello API failed -> " + response);
        return false;
      }
    } else {
      logger.warn("Trello card ID invalid, cannot add comment -> " + ID);
      return false;
    }
  }
}
