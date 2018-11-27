package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KelurahanResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Kelurahan> kelurahanList;

    public KelurahanResponse(String value, List<Kelurahan> kelurahanList) {
        this.value = value;
        this.kelurahanList = kelurahanList;
    }

    public String getValue() {
        return value;
    }

    public List<Kelurahan> getKelurahanList() {
        return kelurahanList;
    }
}
