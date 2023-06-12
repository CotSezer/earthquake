package com.sezer.earthquake.model;


public class Boundary {
    String country;

    float maxLatitude;

    float minLatitude;

    float maxLongitude;

    float minLongitude;

    public Boundary(String country, float maxLatitude, float minLatitude, float maxLongitude, float minLongitude) {
        this.country = country;
        this.maxLatitude = maxLatitude;
        this.minLatitude = minLatitude;
        this.maxLongitude = maxLongitude;
        this.minLongitude = minLongitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(float maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public float getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(float minLatitude) {
        this.minLatitude = minLatitude;
    }

    public float getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(float maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public float getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(float minLongitude) {
        this.minLongitude = minLongitude;
    }
}
