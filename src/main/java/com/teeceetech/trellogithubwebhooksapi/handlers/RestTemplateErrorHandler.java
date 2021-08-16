package com.teeceetech.trellogithubwebhooksapi.handlers;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

  static final Logger logger = LoggerFactory.getLogger(
    RestTemplateErrorHandler.class
  );

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    logger.error(
      "RestTemplate exception, response: {}",
      getResponseBody(response)
    );
    throw new IOException("Rest Template IO Exception");
  }
}
