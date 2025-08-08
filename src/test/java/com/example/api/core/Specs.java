package com.example.api.core;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public final class Specs {
  private Specs() {}

  public static ResponseSpecification json200() {
    return new ResponseSpecBuilder()
        .expectStatusCode(200)
        .expectContentType(ContentType.JSON)
        .build();
  }

  public static ResponseSpecification json201() {
    return new ResponseSpecBuilder()
        .expectStatusCode(201)
        .expectContentType(ContentType.JSON)
        .build();
  }

  public static ResponseSpecification notFound404() {
    return new ResponseSpecBuilder()
        .expectStatusCode(404)
        .build();
  }

  public static ResponseSpecification serverError500() {
    return new ResponseSpecBuilder()
        .expectStatusCode(500)
        .build(); // no content-type expectation; API returns text/html
  }
}
