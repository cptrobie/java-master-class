package com.cptrobie.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileDataAccessService extends UserListDataAccessService implements UserDao {
  private final String filePath;


  public UserFileDataAccessService(String filePath) {
    this.filePath = filePath;
    USERS = UserCsvReader.readUsersCsvToList(filePath);
  }


  @Override
  public List<User> getUsers() {
    return super.getUsers();
  }

  @Override
  public Optional<User> findUserById(UUID userId) {
    return super.findUserById(userId);
  }
}
