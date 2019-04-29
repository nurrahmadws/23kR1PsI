package com.example.matatabi.padm.model;

public class PadmKelurahan {
    private String nm_kelurahan, nm_kecamatan, total_mahasiswa;

    public PadmKelurahan(String nm_kelurahan, String nm_kecamatan, String total_mahasiswa) {
        this.nm_kelurahan = nm_kelurahan;
        this.nm_kecamatan = nm_kecamatan;
        this.total_mahasiswa = total_mahasiswa;
    }

    public String getNm_kelurahan() {
        return nm_kelurahan;
    }

    public String getNm_kecamatan() {
        return nm_kecamatan;
    }

    public String getTotal_mahasiswa() {
        return total_mahasiswa;
    }
}
