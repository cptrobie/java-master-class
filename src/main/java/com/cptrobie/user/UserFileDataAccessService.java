package com.cptrobie.user;

import java.util.Optional;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
  private final String filePath;
  private static User[] USERS;

  public UserFileDataAccessService(String filePath) {
    this.filePath = filePath;
    USERS = UserCsvReader.readUsersCsvToArray(filePath);
  }

  @Override
  public User[] getUsers() {
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
    throw new UserNotFoundException("User with id of \" + userId + \" was not found");
  }
}
