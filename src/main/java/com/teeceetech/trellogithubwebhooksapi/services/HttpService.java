package com.teeceetech.trellogithubwebhooksapi.services;

import com.teeceetech.trellogithubwebhooksapi.web.models.trello.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

  public final RestTemplate restTemplate;

  @Autowired
  public HttpService(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  private void checkNull(String url, RestTemplate restTemplate) {
    if (url == null || url.equals("")) {
      throw new NullPointerException("missing url");
    }

    if (restTemplate == null) {
      throw new NullPointerException("missing rest template");
    }
  }

  public Root searchTrelloCards(String url) {
    checkNull(url, restTemplate);

    return restTemplate.getForObject(url, Root.class);
  }

  public int postToTrello(String url, Object payload) {
    checkNull(url, restTemplate);

    if (payload == null) {
      throw new NullPointerException("missing payload");
    }

    return restTemplate
      .postForEntity(url, payload, String.class)
      .getStatusCodeValue();
  }
}
