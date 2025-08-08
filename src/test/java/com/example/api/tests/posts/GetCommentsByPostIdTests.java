package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

class GetCommentsByPostIdTests {

  @Test
  @DisplayName("GET /comments?postId=1 returns 200 with 5 comments")
  void getCommentsByPostIdQuery_1_ok() {
    var response = PostsClient.getCommentsByPostIdQuery(1)
        .then()
        .spec(Specs.json200())
        .body("size()", equalTo(5))
        .body("postId", everyItem(equalTo(1)))
        .body("[0].name", not(emptyString()))
        .body("[0].email", containsString("@"))
        .body("[0].body", not(emptyString()))
        .extract().response();

    List<Map<String, Object>> comments = response.as(new TypeRef<>() {});
    assertThat(comments).hasSize(5);
    assertThat(comments.get(0)).containsKeys("postId", "id", "name", "email", "body");
  }

  @Test
  @DisplayName("GET /comments?postId=101 returns 200 with empty list")
  void getCommentsByPostIdQuery_101_empty() {
    var response = PostsClient.getCommentsByPostIdQuery(101)
        .then()
        .spec(Specs.json200())
        .body("", hasSize(0))
        .extract().response();

    List<?> comments = response.as(new TypeRef<List<?>>() {});
    assertThat(comments).isEmpty();
  }

  @Test
  @DisplayName("GET /posts/1/comments returns 200 with 5 comments")
  void getCommentsForPost_1_ok() {
    var response = PostsClient.getCommentsForPost(1)
        .then()
        .spec(Specs.json200())
        .body("size()", equalTo(5))
        .body("postId", everyItem(equalTo(1)))
        .body("[0].id", notNullValue())
        .body("[0].name", not(emptyString()))
        .body("[0].email", containsString("@"))
        .body("[0].body", not(emptyString()))
        .extract().response();

    List<Map<String, Object>> comments = response.as(new TypeRef<>() {});
    assertThat(comments).hasSize(5);
    assertThat(comments.get(0)).containsKeys("postId", "id", "name", "email", "body");
  }
}