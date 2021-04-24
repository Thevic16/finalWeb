package edu.pucmm.eict.models;

import java.util.Objects;

public class FormJson {
  private String name;
  private String lastName;
  private String area;
  private String schoolLevel;
  private String latitude;
  private String longitude;
  private String user;
  private boolean status;
  private String id;
  private String photo;


  public FormJson() {
  }


  public FormJson(String name, String lastName, String area, String schoolLevel, String latitude, String longitude, String user, boolean status, String id, String photo) {
    this.name = name;
    this.lastName = lastName;
    this.area = area;
    this.schoolLevel = schoolLevel;
    this.latitude = latitude;
    this.longitude = longitude;
    this.user = user;
    this.status = status;
    this.id = id;
    this.photo = photo;
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

  public String getSchoolLevel() {
    return this.schoolLevel;
  }

  public void setSchoolLevel(String schoolLevel) {
    this.schoolLevel = schoolLevel;
  }

  public String getLatitude() {
    return this.latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public boolean isStatus() {
    return this.status;
  }

  public boolean getStatus() {
    return this.status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPhoto() {
    return this.photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public FormJson name(String name) {
    this.name = name;
    return this;
  }

  public FormJson lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public FormJson area(String area) {
    this.area = area;
    return this;
  }

  public FormJson schoolLevel(String schoolLevel) {
    this.schoolLevel = schoolLevel;
    return this;
  }

  public FormJson latitude(String latitude) {
    this.latitude = latitude;
    return this;
  }

  public FormJson longitude(String longitude) {
    this.longitude = longitude;
    return this;
  }

  public FormJson user(String user) {
    this.user = user;
    return this;
  }

  public FormJson status(boolean status) {
    this.status = status;
    return this;
  }

  public FormJson id(String id) {
    this.id = id;
    return this;
  }

  public FormJson photo(String photo) {
    this.photo = photo;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FormJson)) {
            return false;
        }
        FormJson formJson = (FormJson) o;
        return Objects.equals(name, formJson.name) && Objects.equals(lastName, formJson.lastName) && Objects.equals(area, formJson.area) && Objects.equals(schoolLevel, formJson.schoolLevel) && Objects.equals(latitude, formJson.latitude) && Objects.equals(longitude, formJson.longitude) && Objects.equals(user, formJson.user) && status == formJson.status && Objects.equals(id, formJson.id) && Objects.equals(photo, formJson.photo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, lastName, area, schoolLevel, latitude, longitude, user, status, id, photo);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", area='" + getArea() + "'" +
      ", schoolLevel='" + getSchoolLevel() + "'" +
      ", latitude='" + getLatitude() + "'" +
      ", longitude='" + getLongitude() + "'" +
      ", user='" + getUser() + "'" +
      ", status='" + isStatus() + "'" +
      ", id='" + getId() + "'" +
      ", photo='" + getPhoto() + "'" +
      "}";
  }
  
}