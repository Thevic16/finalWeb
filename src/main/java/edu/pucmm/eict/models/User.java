package edu.pucmm.eict.models;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "USERS")
public class User implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String lastName;
  private String userName;
  private String password;
  private String role;

  public User() {
    
  }

  public User(String name, String lastName, String userName, String password, String role) {
    this.name = name;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.role = role;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public User name(String name) {
    this.name = name;
    return this;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public User userName(String userName) {
    this.userName = userName;
    return this;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  public User role(String role) {
    this.role = role;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, lastName, userName, password, role);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", userName='" + getUserName() + "'" +
      ", password='" + getPassword() + "'" +
      ", role='" + getRole() + "'" +
      "}";
  }


}