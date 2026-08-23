package com.cptrobie.user;

import java.util.UUID;

public record User(UUID id, String name) {

  @Override
  public String toString() {
    return "User {" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
