package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class GetPostByIdTests {

  @Test
  @DisplayName("GET /posts/{id} returns 200 and post payload for id=1")
  void getPostById_1_ok() {
    PostsClient.getPostById(1)
        .then()
        .spec(Specs.json200())
        .body(matchesJsonSchemaInClasspath("schemas/post.json"))
        .body("id", equalTo(1));
  }

  @Test
  @DisplayName("GET /posts/{id} returns 404 for non-existing id=101")
  void getPostById_101_notFound() {
    PostsClient.getPostById(101)
        .then()
        .spec(Specs.notFound404());
  }

  @Test
  @DisplayName("GET /posts/{id} returns 404 for id=0 (invalid)")
  void getPostById_0_notFound() {
    PostsClient.getPostById(0)
        .then()
        .spec(Specs.notFound404());
  }
}
