package edu.pucmm.eict.models;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "POSITIONS")
public class Position implements Serializable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private double latitude;
  private double longitude;


  public Position() {
  }

  public Position(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public Position latitude(double latitude) {
    this.latitude = latitude;
    return this;
  }

  public Position longitude(double longitude) {
    this.longitude = longitude;
    return this;
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return latitude == position.latitude && longitude == position.longitude;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitude, longitude);
  }

  @Override
  public String toString() {
    return "{" +
      " latitude='" + getLatitude() + "'" +
      ", longitude='" + getLongitude() + "'" +
      "}";
  }

}