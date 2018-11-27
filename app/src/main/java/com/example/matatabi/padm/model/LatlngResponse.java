package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatlngResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Latlng> latlngList;

    public LatlngResponse(String value, List<Latlng> latlngList) {
        this.value = value;
        this.latlngList = latlngList;
    }

    public String getValue() {
        return value;
    }

    public List<Latlng> getLatlngList() {
        return latlngList;
    }
}
