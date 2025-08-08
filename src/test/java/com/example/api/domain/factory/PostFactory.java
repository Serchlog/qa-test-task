package com.example.api.domain.factory;

import java.util.UUID;

import com.example.api.domain.models.Post;

public final class PostFactory {
  private PostFactory() {}

  // Valid post payload for create
  public static Post newValidPost() {
    return new Post(
        1,
        null, // server assigns for POST
        "Title " + UUID.randomUUID(),
        "Body " + UUID.randomUUID());
  }

  // Valid post payload for replace (PUT) where ID is set in path
  public static Post newPutPost(int id) {
    return new Post(
        1,
        id,
        "Replaced Title " + UUID.randomUUID(),
        "Replaced Body " + UUID.randomUUID());
  }
}
