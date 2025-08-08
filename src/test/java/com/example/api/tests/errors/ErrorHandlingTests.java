package com.example.api.tests.errors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.example.api.core.RestClient;
import com.example.api.core.Specs;

import static io.restassured.RestAssured.given;

@Tag("negative")
class ErrorHandlingTests {

  @Test
  @DisplayName("GET unknown route returns 404 (negative)")
  void unknownRoute_404() {
    given()
        .spec(RestClient.spec())
        .when()
        .get("/not-a-real-route")
        .then()
        .spec(Specs.notFound404());
  }
}