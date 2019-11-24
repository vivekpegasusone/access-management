package com.drishti.accessmanagement.dto;

import java.io.Serializable;

public class UserLogin implements Serializable {

  private static final long serialVersionUID = 1582391320357715235L;

  private String userId;
  private String password;

  public UserLogin() {
  }

  public UserLogin(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserLogin{" +
        "userId='" + userId + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
