package com.lpnote.demo.entity;

import com.google.common.collect.Range;

/**
 * Created by luopeng on 2017/9/13.
 */
public class FootTruckQuery {

    //纬度范围
    public static final Range<Double> LATITUDE_RANGE = Range.closed(-90d, 90d);
    //经度范围
    public static final Range<Double> LONGITUDE_RANGE = Range.closed(-180d, 180d);

    //默认搜索范围
    public static final int DEFAULT_SEARCH_AREA = 1000;
    //默认返回数量
    public static final int DEFAULT_QUERY_SIZE = 10;

    //最大搜索范围
    public static final int MAX_SEARCH_AREA = 2000;
    //最大返回数量
    public static final int MAX_QUERY_SIZE = 20;

    //纬度
    private double latitude;

    //经度
    private double longitude;

    //搜索范围，单位：米
    private int meters;

    //返回最大数量
    private int querySize;

    public void verify() {
        if (!LATITUDE_RANGE.contains(latitude)) {
            throw new IllegalArgumentException("latitude:" + latitude);
        } else if (!LONGITUDE_RANGE.contains(longitude)) {
            throw new IllegalArgumentException("longitude:" + longitude);
        }

        if (meters <= 0) {
            meters = DEFAULT_SEARCH_AREA;
        } else if (meters > MAX_SEARCH_AREA) {
            meters = MAX_SEARCH_AREA;
        }

        if (querySize <= 0) {
            querySize = DEFAULT_QUERY_SIZE;
        } else if (querySize > MAX_QUERY_SIZE) {
            querySize = MAX_QUERY_SIZE;
        }
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

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }

    public int getQuerySize() {
        return querySize;
    }

    public void setQuerySize(int querySize) {
        this.querySize = querySize;
    }

}
