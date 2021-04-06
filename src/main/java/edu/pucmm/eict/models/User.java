package edu.pucmm.eict.models;

import java.util.*;
import java.io.Serializable;
import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@Table(name = "USERS")
public class User implements Serializable{

  @Id
  private String userName;
  private String name;
  private String lastName;
  private String password;
  private Boolean rolAdmin;
  private Boolean rolPollster;
  private String roles; //This is to print in list-user


  public User() {
    
  }

  public User(String name, String lastName, String userName, String password) {
    this.name = name;
    this.lastName = lastName;
    this.userName = userName;
    this.password = password;
    this.rolAdmin = false;
    this.rolPollster = false;
    this.roles = " ";
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

  public Boolean getRolAdmin() {
    return rolAdmin;
  }

  public void setRolAdmin(Boolean rolAdmin) {
    this.rolAdmin = rolAdmin;
  }

  public Boolean getRolPollster() {
    return rolPollster;
  }

  public void setRolPollster(Boolean rolPollster) {
    this.rolPollster = rolPollster;
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


  public String getRoles() {
    String roles = "";
    if(rolAdmin){
      roles = roles + " Administrador";
    }
    if(rolPollster){
      roles = roles + " Encuestador";
    }

    this.roles = roles;

    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", userName='" + getUserName() + "'" +
      ", password='" + getPassword() + "'" +
      "}";
  }


}