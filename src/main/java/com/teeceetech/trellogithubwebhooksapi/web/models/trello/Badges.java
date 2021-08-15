package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

public class Badges {

  public AttachmentsByType attachmentsByType;
  public boolean location;
  public int votes;
  public boolean viewingMemberVoted;
  public boolean subscribed;
  public String fogbugz;
  public int checkItems;
  public int checkItemsChecked;
  public Object checkItemsEarliestDue;
  public int comments;
  public int attachments;
  public boolean description;
  public Object due;
  public boolean dueComplete;
  public Object start;

  public Badges() {}

  public AttachmentsByType getAttachmentsByType() {
    return attachmentsByType;
  }

  public void setAttachmentsByType(AttachmentsByType attachmentsByType) {
    this.attachmentsByType = attachmentsByType;
  }

  public boolean isLocation() {
    return location;
  }

  public void setLocation(boolean location) {
    this.location = location;
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }

  public boolean isViewingMemberVoted() {
    return viewingMemberVoted;
  }

  public void setViewingMemberVoted(boolean viewingMemberVoted) {
    this.viewingMemberVoted = viewingMemberVoted;
  }

  public boolean isSubscribed() {
    return subscribed;
  }

  public void setSubscribed(boolean subscribed) {
    this.subscribed = subscribed;
  }

  public String getFogbugz() {
    return fogbugz;
  }

  public void setFogbugz(String fogbugz) {
    this.fogbugz = fogbugz;
  }

  public int getCheckItems() {
    return checkItems;
  }

  public void setCheckItems(int checkItems) {
    this.checkItems = checkItems;
  }

  public int getCheckItemsChecked() {
    return checkItemsChecked;
  }

  public void setCheckItemsChecked(int checkItemsChecked) {
    this.checkItemsChecked = checkItemsChecked;
  }

  public Object getCheckItemsEarliestDue() {
    return checkItemsEarliestDue;
  }

  public void setCheckItemsEarliestDue(Object checkItemsEarliestDue) {
    this.checkItemsEarliestDue = checkItemsEarliestDue;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public int getAttachments() {
    return attachments;
  }

  public void setAttachments(int attachments) {
    this.attachments = attachments;
  }

  public boolean isDescription() {
    return description;
  }

  public void setDescription(boolean description) {
    this.description = description;
  }

  public Object getDue() {
    return due;
  }

  public void setDue(Object due) {
    this.due = due;
  }

  public boolean isDueComplete() {
    return dueComplete;
  }

  public void setDueComplete(boolean dueComplete) {
    this.dueComplete = dueComplete;
  }

  public Object getStart() {
    return start;
  }

  public void setStart(Object start) {
    this.start = start;
  }
}
