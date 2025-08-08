package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

class GetPostCommentsTests {

  @Test
  @DisplayName("GET /posts/1/comments matches fetch example (array of 5, fields present)")
  void getCommentsForPost_1_ok() {
    var resp = PostsClient.getCommentsForPost(1)
        .then()
        .spec(Specs.json200())
        .body("size()", equalTo(5))
        .body("[0].postId", equalTo(1))
        .body("[0].id", notNullValue())
        .body("[0].name", notNullValue())
        .body("[0].email", allOf(containsString("@"), not(emptyString())))
        .body("[0].body", notNullValue())
        // Optional: validate entire array schema if you add schemas/comments-array.json
        //.body(matchesJsonSchemaInClasspath("schemas/comments-array.json"))
        .extract().response();

    List<Map<String,Object>> comments = resp.as(new TypeRef<>() {});
    assertThat(comments).hasSize(5);
    assertThat(comments.get(0)).containsKeys("postId","id","name","email","body");
  }

  @Test
  @DisplayName("GET /posts/101/comments empty list (no comments for non-existing post)")
  void getCommentsForPost_101_empty() {
    var resp = PostsClient.getCommentsForPost(101)
        .then()
        .spec(Specs.json200())
        .body("", hasSize(0))
        .extract().response();

    List<?> list = resp.as(new TypeRef<List<?>>() {});
    assertThat(list).isEmpty();
  }
}
