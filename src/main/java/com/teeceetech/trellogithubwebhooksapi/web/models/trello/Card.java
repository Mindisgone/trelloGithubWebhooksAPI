package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

import java.util.Date;
import java.util.List;

public class Card {

  public String id;
  public List<CheckItemState> checkItemStates;
  public boolean closed;
  public Date dateLastActivity;
  public String desc;
  public Object descData;
  public int dueReminder;
  public String idBoard;
  public String idList;
  public List<Object> idMembersVoted;
  public int idShort;
  public Object idAttachmentCover;
  public List<Object> idLabels;
  public boolean manualCoverAttachment;
  public String name;
  public int pos;
  public String shortLink;
  public boolean isTemplate;
  public Object cardRole;
  public boolean dueComplete;
  public Object due;
  public Object email;
  public List<Object> labels;
  public String shortUrl;
  public Object start;
  public String url;
  public List<String> idMembers;
  public Cover cover;
  public Badges badges;
  public boolean subscribed;
  public List<String> idChecklists;

  public Card() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<CheckItemState> getCheckItemStates() {
    return checkItemStates;
  }

  public void setCheckItemStates(List<CheckItemState> checkItemStates) {
    this.checkItemStates = checkItemStates;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public Date getDateLastActivity() {
    return dateLastActivity;
  }

  public void setDateLastActivity(Date dateLastActivity) {
    this.dateLastActivity = dateLastActivity;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Object getDescData() {
    return descData;
  }

  public void setDescData(Object descData) {
    this.descData = descData;
  }

  public int getDueReminder() {
    return dueReminder;
  }

  public void setDueReminder(int dueReminder) {
    this.dueReminder = dueReminder;
  }

  public String getIdBoard() {
    return idBoard;
  }

  public void setIdBoard(String idBoard) {
    this.idBoard = idBoard;
  }

  public String getIdList() {
    return idList;
  }

  public void setIdList(String idList) {
    this.idList = idList;
  }

  public List<Object> getIdMembersVoted() {
    return idMembersVoted;
  }

  public void setIdMembersVoted(List<Object> idMembersVoted) {
    this.idMembersVoted = idMembersVoted;
  }

  public int getIdShort() {
    return idShort;
  }

  public void setIdShort(int idShort) {
    this.idShort = idShort;
  }

  public Object getIdAttachmentCover() {
    return idAttachmentCover;
  }

  public void setIdAttachmentCover(Object idAttachmentCover) {
    this.idAttachmentCover = idAttachmentCover;
  }

  public List<Object> getIdLabels() {
    return idLabels;
  }

  public void setIdLabels(List<Object> idLabels) {
    this.idLabels = idLabels;
  }

  public boolean isManualCoverAttachment() {
    return manualCoverAttachment;
  }

  public void setManualCoverAttachment(boolean manualCoverAttachment) {
    this.manualCoverAttachment = manualCoverAttachment;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPos() {
    return pos;
  }

  public void setPos(int pos) {
    this.pos = pos;
  }

  public String getShortLink() {
    return shortLink;
  }

  public void setShortLink(String shortLink) {
    this.shortLink = shortLink;
  }

  public boolean isTemplate() {
    return isTemplate;
  }

  public void setTemplate(boolean template) {
    isTemplate = template;
  }

  public Object getCardRole() {
    return cardRole;
  }

  public void setCardRole(Object cardRole) {
    this.cardRole = cardRole;
  }

  public boolean isDueComplete() {
    return dueComplete;
  }

  public void setDueComplete(boolean dueComplete) {
    this.dueComplete = dueComplete;
  }

  public Object getDue() {
    return due;
  }

  public void setDue(Object due) {
    this.due = due;
  }

  public Object getEmail() {
    return email;
  }

  public void setEmail(Object email) {
    this.email = email;
  }

  public List<Object> getLabels() {
    return labels;
  }

  public void setLabels(List<Object> labels) {
    this.labels = labels;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }

  public Object getStart() {
    return start;
  }

  public void setStart(Object start) {
    this.start = start;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<String> getIdMembers() {
    return idMembers;
  }

  public void setIdMembers(List<String> idMembers) {
    this.idMembers = idMembers;
  }

  public Cover getCover() {
    return cover;
  }

  public void setCover(Cover cover) {
    this.cover = cover;
  }

  public Badges getBadges() {
    return badges;
  }

  public void setBadges(Badges badges) {
    this.badges = badges;
  }

  public boolean isSubscribed() {
    return subscribed;
  }

  public void setSubscribed(boolean subscribed) {
    this.subscribed = subscribed;
  }

  public List<String> getIdChecklists() {
    return idChecklists;
  }

  public void setIdChecklists(List<String> idChecklists) {
    this.idChecklists = idChecklists;
  }
}
