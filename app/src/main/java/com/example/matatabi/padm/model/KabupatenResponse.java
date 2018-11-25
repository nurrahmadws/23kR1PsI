package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KabupatenResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Kabupaten> kabupatenList;

    public KabupatenResponse(String value, List<Kabupaten> kabupatenList) {
        this.value = value;
        this.kabupatenList = kabupatenList;
    }

    public String getValue() {
        return value;
    }

    public List<Kabupaten> getKabupatenList() {
        return kabupatenList;
    }
}
