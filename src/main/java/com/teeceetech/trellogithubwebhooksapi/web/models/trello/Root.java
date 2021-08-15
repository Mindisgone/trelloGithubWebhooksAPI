package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

import java.util.List;

public class Root {

  public Options options;
  public List<Card> cards;

  public Root() {}

  public Options getOptions() {
    return options;
  }

  public void setOptions(Options options) {
    this.options = options;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }
}
