package com.cptrobie.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
  private final String filePath;
  private static List<User> USERS;

  public UserFileDataAccessService(String filePath) {
    this.filePath = filePath;
    USERS = UserCsvReader.readUsersCsvToList(filePath);
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
