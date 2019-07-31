package com.salam.capstoneprojectstage2.Models;

public class Location_model {

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location_model(String Latitude, String Longitude, String id) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.id = id;
    }

    private String Latitude;
    private String Longitude;

    private String id;

    public Location_model() {
    }
}
