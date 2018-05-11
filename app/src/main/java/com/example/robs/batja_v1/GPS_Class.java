package com.example.robs.batja_v1;

import java.util.Date;

/**
 * Created by Robs on 11.05.18.
 */

public class GPS_Class {

    private int loc_id;
    private Date loc_date;
    private double loc_lat;
    private double loc_lng;
    private double loc_speed;

    public GPS_Class(int loc_id, Date loc_date, double loc_lat, double loc_lng, double loc_speed) {
        this.loc_id = loc_id;
        this.loc_date = loc_date;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
    }

    public GPS_Class() {
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public Date getLoc_date() {
        return loc_date;
    }

    public void setLoc_date(Date loc_date) {
        this.loc_date = loc_date;
    }

    public double getLoc_lat() {
        return loc_lat;
    }

    public void setLoc_lat(double loc_lat) {
        this.loc_lat = loc_lat;
    }

    public double getLoc_lng() {
        return loc_lng;
    }

    public void setLoc_lng(double loc_lng) {
        this.loc_lng = loc_lng;
    }

    public double getLoc_speed() {
        return loc_speed;
    }

    public void setLoc_speed(double loc_speed) {
        this.loc_speed = loc_speed;
    }

}
