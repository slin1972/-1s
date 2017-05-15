package com.zeroxy._1s.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "master_user")
public class MasterUser extends IdDomain implements Serializable{

  private String username ;

  private String password ;

  private String token ;

  private String avatar = "https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png";

  private String nickname = "大牛";

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public String toString() {
    return "MasterUser{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
