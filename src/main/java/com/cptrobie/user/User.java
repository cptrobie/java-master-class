package com.cptrobie.user;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private final UUID id;
    private final String name;


  public User(String name) {
    this(UUID.randomUUID(), name);
  }

  public User(UUID id, String name) {
    this.id = id;
    this.name = name;
  }


  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String serializeUser() {
    return id.toString() + ", " + name;
  }

  @Override
  public String toString() {
    return "User {" + "id=" + id + ", name='" + name + '\'' + '}';
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
