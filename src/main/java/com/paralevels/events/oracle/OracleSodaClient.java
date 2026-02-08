package com.paralevels.events.oracle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OracleSodaClient {

  private final RestClient base;

  public void insertEvent(String url, String user, String pass, Object body) {
    base.post()
        .uri(url)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .headers(h -> h.setBasicAuth(user, pass))
        .body(body)
        .retrieve()
        .toBodilessEntity();
  }
}
