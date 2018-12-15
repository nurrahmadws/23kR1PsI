package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PadmResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Padm> padmList;

    public PadmResponse(String value, List<Padm> padmList) {
        this.value = value;
        this.padmList = padmList;
    }

    public String getValue() {
        return value;
    }

    public List<Padm> getPadmList() {
        return padmList;
    }
}


