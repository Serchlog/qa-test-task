package com.example.api.core;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public final class RestClient {
  private static final RequestSpecification SPEC = new RequestSpecBuilder()
      .setBaseUri(Config.baseUrl())
      .setContentType(ContentType.JSON)
      .setAccept(ContentType.JSON)
      .setConfig(newConfig().redirect(redirectConfig().followRedirects(false)))
      .setRelaxedHTTPSValidation()
      // Log only when validation fails to keep console clean
      .log(LogDetail.URI)
      .build();

  private RestClient() {}

  public static RequestSpecification spec() {
    // RequestSpecification is immutable here, safe for concurrent use.
    return SPEC;
  }
}
