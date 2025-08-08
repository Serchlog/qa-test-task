package com.example.api.tests.posts;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;

import io.restassured.common.mapper.TypeRef;

@Tag("critical")
class GetPostsTests {

  @Test
  @DisplayName("GET /posts matches fetch example (100 items, first has expected keys)")
  void getAllPosts_ok() {
    var resp = PostsClient.getPosts()
        .then()
        .spec(Specs.json200())
        .body("size()", equalTo(100))
        .body("[0].id", equalTo(1))
        .body("[0].userId", notNullValue())
        .body("[0].title", not(emptyString()))
        .body("[0].body", not(emptyString()))
        // Optional: validate whole array schema if you add schemas/posts-array.json
        //.body(matchesJsonSchemaInClasspath("schemas/posts-array.json"))
        .extract().response();

    List<Map<String,Object>> posts = resp.as(new TypeRef<>() {});
    assertThat(posts).hasSize(100);
    assertThat(posts.get(0)).containsKeys("userId","id","title","body");
  }
}
