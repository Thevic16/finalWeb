package edu.pucmm.eict.models;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "FORMS")
public class Form implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String lastName;
  private String area;
  private String nivelEscolar;
  @ManyToOne
  private User user;
  @OneToOne
  private Position position;
  private String photoBase64;


  public Form() {
  }

  public Form(String name, String lastName, String area, String nivelEscolar, User user, Position position, String photoBase64) {
    this.name = name;
    this.lastName = lastName;
    this.area = area;
    this.nivelEscolar = nivelEscolar;
    this.user = user;
    this.position = position;
    this.photoBase64 = photoBase64;
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

  public String getArea() {
    return this.area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getNivelEscolar() {
    return this.nivelEscolar;
  }

  public void setNivelEscolar(String nivelEscolar) {
    this.nivelEscolar = nivelEscolar;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Position getPosition() {
    return this.position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Form name(String name) {
    this.name = name;
    return this;
  }

  public Form lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public Form area(String area) {
    this.area = area;
    return this;
  }

  public Form nivelEscolar(String nivelEscolar) {
    this.nivelEscolar = nivelEscolar;
    return this;
  }

  public Form user(User user) {
    this.user = user;
    return this;
  }

  public Form position(Position position) {
    this.position = position;
    return this;
  }

  public String getPhotoBase64() {
    return this.photoBase64;
  }

  public void setPhotoBase64(String photoBase64) {
    this.photoBase64 = photoBase64;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Form)) {
            return false;
        }
        Form form = (Form) o;
        return Objects.equals(name, form.name) && Objects.equals(lastName, form.lastName) && Objects.equals(area, form.area) && Objects.equals(nivelEscolar, form.nivelEscolar) && Objects.equals(user, form.user) && Objects.equals(position, form.position);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, lastName, area, nivelEscolar, user, position);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", area='" + getArea() + "'" +
      ", nivelEscolar='" + getNivelEscolar() + "'" +
      ", user='" + getUser() + "'" +
      ", position='" + getPosition() + "'" +
      ", photo='" + getPhotoBase64() + "'" +
      "}";
  }


}