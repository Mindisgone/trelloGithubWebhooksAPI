package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

public class CheckItemState {

  public String idCheckItem;
  public String state;

  public CheckItemState() {}

  public String getIdCheckItem() {
    return idCheckItem;
  }

  public void setIdCheckItem(String idCheckItem) {
    this.idCheckItem = idCheckItem;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
