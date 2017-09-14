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
}
