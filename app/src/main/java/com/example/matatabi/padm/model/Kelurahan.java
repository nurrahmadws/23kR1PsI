package com.example.matatabi.padm.model;

public class Kelurahan {
    private int id_kelurahan;
    private String nm_kabupaten, nm_kecamatan, nm_kelurahan;

    public Kelurahan(int id_kelurahan, String nm_kabupaten, String nm_kecamatan, String nm_kelurahan) {
        this.id_kelurahan = id_kelurahan;
        this.nm_kabupaten = nm_kabupaten;
        this.nm_kecamatan = nm_kecamatan;
        this.nm_kelurahan = nm_kelurahan;
    }

    public int getId_kelurahan() {
        return id_kelurahan;
    }

    public String getNm_kabupaten() {
        return nm_kabupaten;
    }

    public String getNm_kecamatan() {
        return nm_kecamatan;
    }

    public String getNm_kelurahan() {
        return nm_kelurahan;
    }
}
