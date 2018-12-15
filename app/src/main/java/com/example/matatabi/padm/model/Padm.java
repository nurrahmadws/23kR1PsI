package com.example.matatabi.padm.model;

public class Padm {
    String nm_kabupaten, total_mahasiswa;

    public Padm(String nm_kabupaten, String total_mahasiswa) {
        this.nm_kabupaten = nm_kabupaten;
        this.total_mahasiswa = total_mahasiswa;
    }

    public String getNm_kabupaten() {
        return nm_kabupaten;
    }

    public String getTotal_mahasiswa() {
        return total_mahasiswa;
    }
}
