package com.lpnote.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 食物餐车实体
 * Created by luopeng on 2017/9/13.
 */
public class FootTruck {

    /**
     * 餐车名
     */
    @JsonProperty("applicant")
    private String applicant;

    /**
     * 餐车地址
     */
    @JsonProperty("address")
    private String address;

    /**
     * 经营项目
     */
    @JsonProperty("fooditems")
    private String items;

    /**
     * 纬度
     */
    @JsonProperty("latitude")
    private double latitude;

    /**
     * 经度
     */
    @JsonProperty("longitude")
    private double longitude;

    /**
     * 营业时间
     */
    @JsonProperty("dayshours")
    private String dayshours;

    /**
     * 过期时间
     */
    @JsonProperty("expirationdate")
    private String expirationDate;

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDayshours() {
        return dayshours;
    }

    public void setDayshours(String dayshours) {
        this.dayshours = dayshours;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FootTruck footTruck = (FootTruck) o;

        if (Double.compare(footTruck.latitude, latitude) != 0)
            return false;
        if (Double.compare(footTruck.longitude, longitude) != 0)
            return false;
        if (applicant != null ? !applicant.equals(footTruck.applicant) : footTruck.applicant != null)
            return false;
        if (address != null ? !address.equals(footTruck.address) : footTruck.address != null)
            return false;
        if (items != null ? !items.equals(footTruck.items) : footTruck.items != null)
            return false;
        if (dayshours != null ? !dayshours.equals(footTruck.dayshours) : footTruck.dayshours != null)
            return false;
        return expirationDate != null ? expirationDate.equals(footTruck.expirationDate) : footTruck.expirationDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = applicant != null ? applicant.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dayshours != null ? dayshours.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }
}
