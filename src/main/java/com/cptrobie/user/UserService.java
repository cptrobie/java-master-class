package com.cptrobie.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public List<User> getAllUsers() {
    return userDao.getUsers();
  }

  public Optional<User> getUserById(UUID userId) {
    return userDao.findUserById(userId);
  }
}
