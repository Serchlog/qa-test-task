package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import com.example.api.domain.factory.PostFactory;
import com.example.api.domain.models.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class CreatePostTests {

  @Test
  @DisplayName("POST /posts returns 201 with echoed post and new id")
  void createPost_ok() {
    Post payload = PostFactory.newValidPost();

    PostsClient.createPost(payload)
        .then()
        .spec(Specs.json201())
        .body("title", equalTo(payload.getTitle()))
        .body("body", equalTo(payload.getBody()))
        .body("userId", equalTo(payload.getUserId()))
        .body("id", notNullValue()); // JSONPlaceholder returns id=101
  }

  @Test
  @DisplayName("POST /posts with empty body still returns 201 (service-specific behavior)")
  void createPost_emptyBody_serviceBehavior() {
    PostsClient.createPost(new Post())
        .then()
        .spec(Specs.json201())
        .body("id", notNullValue());
  }
}
