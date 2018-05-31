package com.example.robs.batja_v1;

import java.util.Date;

/**
 * Created by Robs on 11.05.18.
 */

public class GPS_Class {

    private int loc_id;
    private int user_id_fk;
    private Date loc_date;
    private double loc_lat;
    private double loc_lng;
    private double loc_speed;
    private double accel;
    private double accelDir;
    //private float loc_lat;
    //private float loc_lng;
    //private float loc_speed;


    public GPS_Class(double loc_lat, double loc_lng, double loc_speed, double accel) {
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
        this.accel = accel;
    }

    public GPS_Class(double loc_lat, double loc_lng, double loc_speed) {
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
    }

    public GPS_Class(Date loc_date, double loc_lat, double loc_lng, double loc_speed) {
        this.loc_date = loc_date;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
    }

    public GPS_Class(int loc_id, int user_id_fk, Date loc_date, double loc_lat, double loc_lng, double loc_speed) {
        this.loc_id = loc_id;
        this.user_id_fk = user_id_fk;
        this.loc_date = loc_date;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
    }

    public GPS_Class(int loc_id, int user_id_fk, double loc_lat, double loc_lng, double loc_speed) {
        this.loc_id = loc_id;
        this.user_id_fk = user_id_fk;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
    }

    public GPS_Class(int loc_id, int user_id_fk, double loc_lat, double loc_lng, double loc_speed, double accel) {
        this.loc_id = loc_id;
        this.user_id_fk = user_id_fk;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
        this.accel = accel;
    }

    public GPS_Class(int loc_id, int user_id_fk, Date loc_date, double loc_lat, double loc_lng, double loc_speed, double accel) {
        this.loc_id = loc_id;
        this.user_id_fk = user_id_fk;
        this.loc_date = loc_date;
        this.loc_lat = loc_lat;
        this.loc_lng = loc_lng;
        this.loc_speed = loc_speed;
        this.accel = accel;
    }

    public GPS_Class() {
    }

    public double getAccel() {
        return accel;
    }

    public void setAccel(double accel) {
        this.accel = accel;
    }

    public double getAccelDir() {
        return accelDir;
    }

    public void setAccelDir(double accelDir) {
        this.accelDir = accelDir;
    }

    public int getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(int user_id_fk) {
        this.user_id_fk = user_id_fk;
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
