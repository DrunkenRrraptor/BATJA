package com.example.robs.batja_v1;

/**
 * Created by Robs on 31.03.18.
 */

public class User_Class {

    private int id;
    private String name;
    private String password;

    public User_Class(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User_Class() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
