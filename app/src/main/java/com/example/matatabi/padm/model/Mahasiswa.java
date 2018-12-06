package com.example.matatabi.padm.model;

public class Mahasiswa {
    private String nim;
    private String username;
    private String nama;
    private String no_hp;
    private String jk;
    private String fakultas;
    private String prodi;
    private String angkatan;
    private String provinsi;
    private String nm_kabupaten;
    private String nm_kecamatan;
    private String nm_kelurahan;
    private String nm_lat;
    private String nm_lng;

    public Mahasiswa(String nim, String username, String nama, String no_hp, String jk, String fakultas, String prodi, String angkatan, String provinsi, String nm_kabupaten, String nm_kecamatan, String nm_kelurahan, String nm_lat, String nm_lng) {
        this.nim = nim;
        this.username = username;
        this.nama = nama;
        this.no_hp = no_hp;
        this.jk = jk;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.angkatan = angkatan;
        this.provinsi = provinsi;
        this.nm_kabupaten = nm_kabupaten;
        this.nm_kecamatan = nm_kecamatan;
        this.nm_kelurahan = nm_kelurahan;
        this.nm_lat = nm_lat;
        this.nm_lng = nm_lng;
    }

    public String getNim() {
        return nim;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getJk() {
        return jk;
    }

    public String getFakultas() {
        return fakultas;
    }

    public String getProdi() {
        return prodi;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public String getProvinsi() {
        return provinsi;
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

    public String getNm_lat() {
        return nm_lat;
    }

    public String getNm_lng() {
        return nm_lng;
    }
}
