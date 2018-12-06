package com.example.matatabi.padm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponse {
    @SerializedName("value")
    private String value;

    @SerializedName("hasil")
    private List<Mahasiswa> mahasiswaList;

    public MahasiswaResponse(String value, List<Mahasiswa> mahasiswaList) {
        this.value = value;
        this.mahasiswaList = mahasiswaList;
    }

    public String getValue() {
        return value;
    }

    public List<Mahasiswa> getMahasiswaList() {
        return mahasiswaList;
    }
}
