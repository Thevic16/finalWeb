package edu.pucmm.eict.models;

import java.util.Objects;

public class FormSoap {
  private String id;
  private String name;
  private String lastName;
  private String area;
  private String schoolLevel;
  private String latitude;
  private String longitude;
  private String user;
  private String photoBase64;
  
  

  public FormSoap() {
  }

  public FormSoap(String id, String name, String lastName, String area, String schoolLevel, String latitude, String longitude, String user, String photoBase64) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.area = area;
    this.schoolLevel = schoolLevel;
    this.latitude = latitude;
    this.longitude = longitude;
    this.user = user;
    this.photoBase64 = photoBase64;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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

  public String getPhotoBase64() {
    return this.photoBase64;
  }

  public void setPhotoBase64(String photoBase64) {
    this.photoBase64 = photoBase64;
  }

  public FormSoap id(String id) {
    this.id = id;
    return this;
  }

  public FormSoap name(String name) {
    this.name = name;
    return this;
  }

  public FormSoap lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public FormSoap area(String area) {
    this.area = area;
    return this;
  }

  public FormSoap schoolLevel(String schoolLevel) {
    this.schoolLevel = schoolLevel;
    return this;
  }

  public FormSoap latitude(String latitude) {
    this.latitude = latitude;
    return this;
  }

  public FormSoap longitude(String longitude) {
    this.longitude = longitude;
    return this;
  }

  public FormSoap user(String user) {
    this.user = user;
    return this;
  }

  public FormSoap photoBase64(String photoBase64) {
    this.photoBase64 = photoBase64;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FormSoap)) {
            return false;
        }
        FormSoap formSoap = (FormSoap) o;
        return Objects.equals(id, formSoap.id) && Objects.equals(name, formSoap.name) && Objects.equals(lastName, formSoap.lastName) && Objects.equals(area, formSoap.area) && Objects.equals(schoolLevel, formSoap.schoolLevel) && Objects.equals(latitude, formSoap.latitude) && Objects.equals(longitude, formSoap.longitude) && Objects.equals(user, formSoap.user) && Objects.equals(photoBase64, formSoap.photoBase64);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lastName, area, schoolLevel, latitude, longitude, user, photoBase64);
  }

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", area='" + getArea() + "'" +
      ", schoolLevel='" + getSchoolLevel() + "'" +
      ", latitude='" + getLatitude() + "'" +
      ", longitude='" + getLongitude() + "'" +
      ", user='" + getUser() + "'" +
      ", photoBase64='" + getPhotoBase64() + "'" +
      "}";
  }



}