package com.cptrobie.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserListDataAccessService implements UserDao {
  protected static List<User> USERS;

  static {
    USERS = List.of(
        new User(UUID.fromString("b610aed5-f556-49c1-bb09-7c23bec47126"), "Alex"),
        new User(UUID.fromString("8ca51d2b-aaaf-4bf2-834a-e02964e10fc3"), "James"),
        new User(UUID.fromString("b10d126a-3608-4980-9f9c-aa179f5cebc3"), "Jamila"),
        new User(UUID.fromString("75568494-7c48-426b-b9f5-6d08d750a69e"), "Lilly"),
        new User(UUID.fromString("752bfaa4-8fb0-456d-a920-6eb7fbc0beea"), "Owen")
    );
  }


  @Override
  public List<User> getUsers() {
    return USERS;
  }

  @Override
  public Optional<User> findUserById(UUID userId) {

    if (userId == null) {
      return Optional.empty();
    }

    for (User user : USERS) {
      if (user != null && user.getId().equals(userId)) {
        return Optional.of(user);
      }
    }
    return Optional.empty();
  }
}
