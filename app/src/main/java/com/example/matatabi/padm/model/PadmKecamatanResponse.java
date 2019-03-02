package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PadmKecamatanResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<PadmKecamatan> padmKecamatanList;

    public PadmKecamatanResponse(String value, List<PadmKecamatan> padmKecamatanList) {
        this.value = value;
        this.padmKecamatanList = padmKecamatanList;
    }

    public String getValue() {
        return value;
    }

    public List<PadmKecamatan> getPadmKecamatanList() {
        return padmKecamatanList;
    }
}
