package com.teeceetech.trellogithubwebhooksapi.web.controllers;

import com.teeceetech.trellogithubwebhooksapi.services.EventService;
import com.teeceetech.trellogithubwebhooksapi.web.models.github.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Github {

  private final EventService eventService;

  @Autowired
  public Github(EventService eventService) {
    this.eventService = eventService;
  }

  @RequestMapping(
    value = "/api/{trelloKey}/{token}",
    method = RequestMethod.POST
  )
  public void receiveGithubMessage(
    @RequestBody Root payload,
    @PathVariable String trelloKey,
    @PathVariable String token,
    @RequestHeader("X-Github-Event") String event
  ) {
    eventService.handleEvent(
      eventService.receiveEvent(payload, trelloKey, token, event)
    );
  }
}
