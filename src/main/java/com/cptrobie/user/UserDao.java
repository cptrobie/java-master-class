package com.cptrobie.user;

import java.util.UUID;

public class UserDao {
  private static final User[] users;

  static {
    users =
        new User[] {
          new User(UUID.fromString("b610aed5-f556-49c1-bb09-7c23bec47126"), "Alex"),
          new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
          new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"),
          new User(UUID.fromString("75568494-7c48-426b-b9f5-6d08d750a69e"), "Lilly"),
          new User(UUID.fromString("752bfaa4-8fb0-456d-a920-6eb7fbc0beea"), "Owen")
        };
  }

  public User[] getUsers() {
    return users;
  }
}
