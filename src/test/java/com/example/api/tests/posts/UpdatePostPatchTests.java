package com.example.api.tests.posts;

import com.example.api.core.PostsClient;
import com.example.api.core.Specs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class UpdatePostPatchTests {

  @Test
  @DisplayName("PATCH /posts/{id} partially updates and returns 200 with merged fields")
  void patchPost_ok() {
    int id = 1;
    Map<String, Object> patch = Map.of("title", "Patched Title");

    PostsClient.updatePostPatch(id, patch)
        .then()
        .spec(Specs.json200())
        .body("id", equalTo(id))
        .body("title", equalTo("Patched Title"));
  }

  @Test
  @DisplayName("PATCH /posts/{id} with empty patch still returns 200 per service behavior")
  void patchPost_emptyBody_serviceBehavior() {
    int id = 1;

    PostsClient.updatePostPatch(id, Map.of())
        .then()
        .spec(Specs.json200())
        .body("id", notNullValue());
  }
}
