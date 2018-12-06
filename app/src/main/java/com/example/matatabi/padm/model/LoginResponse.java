package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private Boolean value;

    private String message;

    private Users user;

    private String level;

    public LoginResponse(Boolean value, String message, Users user, String level) {
        this.value = value;
        this.message = message;
        this.user = user;
        this.level = level;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
