package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PadmKelurahanResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<PadmKelurahan> padmKelurahanList;

    public PadmKelurahanResponse(String value, List<PadmKelurahan> padmKelurahanList) {
        this.value = value;
        this.padmKelurahanList = padmKelurahanList;
    }

    public String getValue() {
        return value;
    }

    public List<PadmKelurahan> getPadmKelurahanList() {
        return padmKelurahanList;
    }
}
