package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DeletePostTests {

  @Test
  @DisplayName("DELETE /posts/{id} returns 200 with empty object")
  void deletePost_ok() {
    var response = PostsClient.deletePost(1)
        .then()
        .spec(Specs.json200())
        .extract().response();

    Map<String, Object> body = response.as(new TypeRef<>() {});
    assertThat(body).isEmpty();
  }

  @Test
  @DisplayName("DELETE /posts/{id} non-existing still returns 200 per service behavior")
  void deletePost_nonExisting_ok() {
    var response = PostsClient.deletePost(101)
        .then()
        .spec(Specs.json200())
        .extract().response();

    Map<String, Object> body = response.as(new TypeRef<>() {});
    assertThat(body).isEmpty();
  }
}
