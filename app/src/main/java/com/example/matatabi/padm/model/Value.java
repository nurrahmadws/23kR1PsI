package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    private String value;

    @SerializedName("message")
    private String message;

    public Value(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
