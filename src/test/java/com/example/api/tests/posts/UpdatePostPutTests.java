package com.example.api.tests.posts;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import com.example.api.domain.models.Post;

class UpdatePostPutTests {

  @Test
  @Tag("critical")
  @DisplayName("PUT /posts/1 matches fetch example payload")
  void putPost_fetchExample_ok() {
    Post payload = new Post();
    payload.setId(1);
    payload.setTitle("foo");
    payload.setBody("bar");
    payload.setUserId(1);

    PostsClient.updatePostPut(1, payload)
        .then()
        .spec(Specs.json200())
        .body("id", equalTo(1))
        .body("title", equalTo("foo"))
        .body("body", equalTo("bar"))
        .body("userId", equalTo(1));
  }

  @Test
  @DisplayName("PUT /posts/{id} existing id=1 still ok")
  void putPost_ok() {
    putPost_fetchExample_ok(); // reuse
  }

  @Test
  @Tag("negative") // upstream may return 500 text/html
  @DisplayName("PUT /posts/{id} non-existing id returns 200 or 500 (unstable upstream)")
  void putPost_nonExisting_flexible() {
    int id = 101;
    Post p = new Post();
    p.setId(id);
    p.setTitle("foo");
    p.setBody("bar");
    p.setUserId(1);

    var response = PostsClient.updatePostPut(id, p)
        .then()
        .statusCode(anyOf(is(200), is(500)))
        .extract();

    if (response.statusCode() == 200) {
      response.body()
          .jsonPath()
          .getMap("");
    } else {
      // If 500 (HTML), skip JSON assertions
      assumeTrue(response.statusCode() == 500);
    }
  }
}
