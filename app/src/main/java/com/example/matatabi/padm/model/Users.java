package com.example.matatabi.padm.model;

public class Users {
    private int id_user;
    private String username;
    private String level;
    private String password;

    public Users(int id_user, String username, String level, String password) {
        this.id_user = id_user;
        this.username = username;
        this.level = level;
        this.password = password;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
