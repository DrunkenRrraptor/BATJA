package com.example.robs.batja_v1;

/**
 * Created by Robs on 31.03.18.
 */

public class User_Class {

    private int users_id_global;
    private String users_name;
    private String users_password;

    public User_Class(int id, String name, String password) {
        this.users_id_global = id;
        this.users_name = name;
        this.users_password = password;
    }

    public User_Class(String users_name, String users_password) {
        this.users_name = users_name;
        this.users_password = users_password;
    }

    public User_Class() {
    }

    public int getUsers_id_global() {
        return users_id_global;
    }

    public void setUsers_id_global(int users_id_global) {
        this.users_id_global = users_id_global;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public String getUsers_password() {
        return users_password;
    }

    public void setUsers_password(String users_password) {
        this.users_password = users_password;
    }
}
