package com.example.matatabi.padm.model;

public class Kecamatan {
    private int id_kecamatan;
    private String nm_kabupaten, nm_kecamatan;

    public Kecamatan(int id_kecamatan, String nm_kabupaten, String nm_kecamatan) {
        this.id_kecamatan = id_kecamatan;
        this.nm_kabupaten = nm_kabupaten;
        this.nm_kecamatan = nm_kecamatan;
    }

    public int getId_kecamatan() {
        return id_kecamatan;
    }

    public String getNm_kabupaten() {
        return nm_kabupaten;
    }

    public String getNm_kecamatan() {
        return nm_kecamatan;
    }
}
