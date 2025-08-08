package com.example.api.core;

import com.example.api.domain.models.Post;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class PostsClient {
  private PostsClient() {}

  public static Response getPosts() {
    return given().spec(RestClient.spec())
        .when().get("/posts");
  }

  public static Response getPostById(int id) {
    return given().spec(RestClient.spec())
        .pathParam("id", id)
        .when().get("/posts/{id}");
  }

  public static Response getCommentsForPost(int id) {
    return given().spec(RestClient.spec())
        .pathParam("id", id)
        .when().get("/posts/{id}/comments");
  }

  public static Response getCommentsByPostIdQuery(int id) {
    return given().spec(RestClient.spec())
        .queryParam("postId", id)
        .when().get("/comments");
  }

  public static Response createPost(Post post) {
    return given().spec(RestClient.spec())
        .body(post)
        .when().post("/posts");
  }

  public static Response updatePostPut(int id, Post post) {
    return given().spec(RestClient.spec())
        .pathParam("id", id)
        .body(post)
        .when().put("/posts/{id}");
  }

  public static Response updatePostPatch(int id, Map<String, Object> patch) {
    return given().spec(RestClient.spec())
        .pathParam("id", id)
        .body(patch)
        .when().patch("/posts/{id}");
  }

  public static Response deletePost(int id) {
    return given().spec(RestClient.spec())
        .pathParam("id", id)
        .when().delete("/posts/{id}");
  }
}
