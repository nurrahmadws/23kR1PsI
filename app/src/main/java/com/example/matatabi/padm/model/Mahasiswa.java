package com.example.matatabi.padm.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Mahasiswa {
    private String nim;
    private String username;
    private String nik;
    private String nama;
    private String jk;
    private String tempat_lahir;
    private String tgl_lahir;
    private String no_hp;
    private String email;
    private String fakultas;
    private String prodi;
    private String angkatan;
    private String kelas;
    private String provinsi;
    private String nm_kabupaten;
    private String nm_kecamatan;
    private String nm_kelurahan;
    private String nm_lat;
    private String nm_lng;
    private String alamat_sekarang;
    private String lat_alamat_sekarang;
    private String lng_alamat_sekarang;
    private String image;

    public Mahasiswa(String nim, String username, String nik, String nama, String jk, String tempat_lahir, String tgl_lahir, String no_hp, String email, String fakultas, String prodi, String angkatan, String kelas, String provinsi, String nm_kabupaten, String nm_kecamatan, String nm_kelurahan, String nm_lat, String nm_lng, String alamat_sekarang, String lat_alamat_sekarang, String lng_alamat_sekarang, String image) {
        this.nim = nim;
        this.username = username;
        this.nik = nik;
        this.nama = nama;
        this.jk = jk;
        this.tempat_lahir = tempat_lahir;
        this.tgl_lahir = tgl_lahir;
        this.no_hp = no_hp;
        this.email = email;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.angkatan = angkatan;
        this.kelas = kelas;
        this.provinsi = provinsi;
        this.nm_kabupaten = nm_kabupaten;
        this.nm_kecamatan = nm_kecamatan;
        this.nm_kelurahan = nm_kelurahan;
        this.nm_lat = nm_lat;
        this.nm_lng = nm_lng;
        this.alamat_sekarang = alamat_sekarang;
        this.lat_alamat_sekarang = lat_alamat_sekarang;
        this.lng_alamat_sekarang = lng_alamat_sekarang;
        this.image = image;
    }

    public String getNim() {
        return nim;
    }

    public String getUsername() {
        return username;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getJk() {
        return jk;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getEmail() {
        return email;
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

    public String getKelas() {
        return kelas;
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

    public String getAlamat_sekarang() {
        return alamat_sekarang;
    }

    public String getLat_alamat_sekarang() {
        return lat_alamat_sekarang;
    }

    public String getLng_alamat_sekarang() {
        return lng_alamat_sekarang;
    }

    public String getImage() {
        return image;
    }
}
