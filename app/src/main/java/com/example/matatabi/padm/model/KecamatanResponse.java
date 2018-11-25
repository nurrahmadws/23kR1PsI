package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KecamatanResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Kecamatan> kecamatanList;

    public KecamatanResponse(String value, List<Kecamatan> kecamatanList) {
        this.value = value;
        this.kecamatanList = kecamatanList;
    }

    public String getValue() {
        return value;
    }

    public List<Kecamatan> getKecamatanList() {
        return kecamatanList;
    }
}
