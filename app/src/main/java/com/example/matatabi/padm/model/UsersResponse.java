package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {

    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Users> usersList;

    public UsersResponse(String value, List<Users> usersList) {
        this.value = value;
        this.usersList = usersList;
    }

    public String getValue() {
        return value;
    }

    public List<Users> getUsersList() {
        return usersList;
    }
}
