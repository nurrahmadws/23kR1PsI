package com.example.matatabi.padm.model;

public class PadmKecamatan {
    private String nm_kecamatan, nm_kabupaten, total_mahasiswa;

    public PadmKecamatan(String nm_kecamatan, String nm_kabupaten, String total_mahasiswa) {
        this.nm_kecamatan = nm_kecamatan;
        this.nm_kabupaten = nm_kabupaten;
        this.total_mahasiswa = total_mahasiswa;
    }

    public String getNm_kecamatan() {
        return nm_kecamatan;
    }

    public String getNm_kabupaten() {
        return nm_kabupaten;
    }

    public String getTotal_mahasiswa() {
        return total_mahasiswa;
    }
}
