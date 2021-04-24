package edu.pucmm.eict.models;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

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
  @Column(length = 16777216)
  private String photo;


  public Form() {
  }

  

  public Form(String name, String lastName, String area, String nivelEscolar, User user, Position position, String photo) {
    this.name = name;
    this.lastName = lastName;
    this.area = area;
    this.nivelEscolar = nivelEscolar;
    this.user = user;
    this.position = position;
    this.photo = photo;
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

  public String getPhoto() {
    return this.photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Form id(int id) {
    this.id = id;
    return this;
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

  public Form photo(String photo) {
    this.photo = photo;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Form)) {
            return false;
        }
        Form form = (Form) o;
        return id == form.id && Objects.equals(name, form.name) && Objects.equals(lastName, form.lastName) && Objects.equals(area, form.area) && Objects.equals(nivelEscolar, form.nivelEscolar) && Objects.equals(user, form.user) && Objects.equals(position, form.position) && Objects.equals(photo, form.photo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lastName, area, nivelEscolar, user, position, photo);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", area='" + getArea() + "'" +
      ", nivelEscolar='" + getNivelEscolar() + "'" +
      ", user='" + getUser() + "'" +
      ", position='" + getPosition() + "'" +
      ", photo='" + getPhoto() + "'" +
      "}";
  }

}