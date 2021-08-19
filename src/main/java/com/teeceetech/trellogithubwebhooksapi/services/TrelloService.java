package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.github.Attachment;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Card;
import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrelloService {

  static final Logger logger = LoggerFactory.getLogger(TrelloService.class);
  private final HttpService httpService;

  @Autowired
  public TrelloService(HttpService httpService) {
    this.httpService = httpService;
  }

  private void checkNull(
    String trelloKey,
    String token,
    HttpService httpService
  ) {
    if (trelloKey == null || trelloKey.equals("")) {
      throw new NullPointerException("missing key");
    }

    if (token == null || token.equals("")) {
      throw new NullPointerException("missing token");
    }

    if (httpService == null) {
      throw new NullPointerException("missing http service");
    }
  }

  public String getCardId(String name, String trelloKey, String token) {
    if (name == null || name.equals("")) {
      throw new NullPointerException("missing name string");
    }

    checkNull(trelloKey, token, httpService);

    String cardId = "";
    String url =
      "https://api.trello.com/1/search?modelTypes=cards&query=" +
      name +
      "&key=" +
      trelloKey +
      "&token=" +
      token;

    com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root root = httpService.searchTrelloCards(
      url
    );

    if (root != null && root.cards != null && root.cards.size() > 0) {
      for (Card card : root.cards) {
        if (
          card != null &&
          card.name != null &&
          !card.name.equals("") &&
          card.name.equals(name)
        ) {
          cardId = card.id;
          logger.info("Trello card found");
        }
      }
    } else {
      logger.warn("Did not find Trello card");
    }

    return cardId;
  }

  public Boolean addCardAttachment(
    String ID,
    String attachmentURL,
    String trelloKey,
    String token
  ) {
    if (ID == null || ID.equals("")) {
      throw new NullPointerException("missing card ID");
    }

    if (attachmentURL == null || attachmentURL.equals("")) {
      throw new NullPointerException("missing attachment URL");
    }

    checkNull(trelloKey, token, httpService);

    String url =
      "https://api.trello.com/1/cards/" +
      ID +
      "/attachments?key=" +
      trelloKey +
      "&token=" +
      token;
    Attachment attachment = new Attachment();
    attachment.setUrl(attachmentURL);

    if (ID.length() > 1) {
      int response = httpService.postToTrello(url, attachment);

      if (response == 200) {
        logger.info("Link added to Trello card, response = " + response);
        return true;
      } else {
        logger.warn(
          "Attach link to Trello card failed, response = " + response
        );
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
    if (ID == null || ID.equals("")) {
      throw new NullPointerException("missing card ID");
    }
    if (text == null || text.equals("")) {
      throw new NullPointerException("missing comment text");
    }

    checkNull(trelloKey, token, httpService);

    String url =
      "https://api.trello.com/1/cards/" +
      ID +
      "/actions/comments?key=" +
      trelloKey +
      "&token=" +
      token;
    Term term = new Term();
    term.setText(text);

    if (ID.length() > 1) {
      int response = httpService.postToTrello(url, term);

      if (response == 200) {
        logger.info("Comment added to Trello card, response = " + response);
        return true;
      } else {
        logger.warn("Comment to Trello API failed -> " + response);
        return false;
      }
    } else {
      logger.warn("Trello card ID invalid, cannot add comment -> " + ID);
      return false;
    }
  }
}
