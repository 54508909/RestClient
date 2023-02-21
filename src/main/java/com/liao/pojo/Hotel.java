package com.liao.pojo;

import java.math.BigDecimal;

public class Hotel {

    private Integer id;
    private String name;
    private String address;
    private BigDecimal price;
    private BigDecimal score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String latitude;
    private String Longitude;
    private String pic;


  @Override
  public String toString() {
    return "Hotel{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", price=" + price +
            ", score=" + score +
            ", brand='" + brand + '\'' +
            ", city='" + city + '\'' +
            ", starName='" + starName + '\'' +
            ", business='" + business + '\'' +
            ", latitude='" + latitude + '\'' +
            ", Longitude='" + Longitude + '\'' +
            ", pic='" + pic + '\'' +
            '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getScore() {
    return score;
  }

  public void setScore(BigDecimal score) {
    this.score = score;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStarName() {
    return starName;
  }

  public void setStarName(String starName) {
    this.starName = starName;
  }

  public String getBusiness() {
    return business;
  }

  public void setBusiness(String business) {
    this.business = business;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return Longitude;
  }

  public void setLongitude(String longitude) {
    Longitude = longitude;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }
}
