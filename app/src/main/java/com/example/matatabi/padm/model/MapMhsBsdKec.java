package com.example.matatabi.padm.model;

public class MapMhsBsdKec {
    private String nim, nama, angkatan, nm_kecamatan, alamat_sekarang, lat_alamat_sekarang, lng_alamat_sekarang;

    public MapMhsBsdKec(String nim, String nama, String angkatan, String nm_kecamatan, String alamat_sekarang, String lat_alamat_sekarang, String lng_alamat_sekarang) {
        this.nim = nim;
        this.nama = nama;
        this.angkatan = angkatan;
        this.nm_kecamatan = nm_kecamatan;
        this.alamat_sekarang = alamat_sekarang;
        this.lat_alamat_sekarang = lat_alamat_sekarang;
        this.lng_alamat_sekarang = lng_alamat_sekarang;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public String getNm_kecamatan() {
        return nm_kecamatan;
    }

    public String getAlamat_sekarang() {
        return alamat_sekarang;
    }

    public String getLat_alamat_sekarang() {
        return lat_alamat_sekarang;
    }

    public String getLng_alamat_sekarang() {
        return lng_alamat_sekarang;
    }
}
