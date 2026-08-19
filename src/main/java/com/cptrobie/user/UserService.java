package com.cptrobie.user;

import java.util.UUID;

public class UserService {
  private final UserDao userDao;

  public UserService() {
    this.userDao = new UserDao();
  }

  public User[] getAllUsers() {
    return userDao.getUsers();
  }

  public User getUserById(UUID userId) {
    for (User user : getAllUsers()) {
      if (user.id().equals(userId)) {
        return user;
      }
    }
    return null;
  }
}
