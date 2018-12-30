package com.example.matatabi.padm.api;

import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.KecamatanResponse;
import com.example.matatabi.padm.model.KelurahanResponse;
import com.example.matatabi.padm.model.LatlngResponse;
import com.example.matatabi.padm.model.LoginResponse;
import com.example.matatabi.padm.model.MahasiswaResponse;
import com.example.matatabi.padm.model.PadmResponse;
import com.example.matatabi.padm.model.UsersResponse;
import com.example.matatabi.padm.model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {

//    LOGIN
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password);
//    Users
    @GET("users/read.php")
    Call<UsersResponse> readUsers();

    @FormUrlEncoded
    @POST("users/create.php")
    Call<Value> addUser(@Field("username") String username,
                        @Field("password") String password,
                        @Field("level") String level);

    @FormUrlEncoded
    @POST("users/edit.php")
    Call<Value> editUser(@Field("id_user") String id_user,
                         @Field("username") String username,
                         @Field("password") String password,
                         @Field("level") String level);

    @FormUrlEncoded
    @POST("users/editLog.php")
    Call<Value> editUserLog(
                            @Field("username") String username,
                            @Field("password") String password,
                            @Field("level") String level);

    @FormUrlEncoded
    @POST("users/delete.php")
    Call<Value> deleteUser(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("users/deleteLog.php")
    Call<Value> deleteUserLog(@Field("username") String username);

    @FormUrlEncoded
    @POST("users/search.php")
    Call<UsersResponse> searchUs(@Field("searchUs") String searchUs);
//    Kabupaten
    @GET("kabupaten/read.php")
    Call<KabupatenResponse> readKabkot();

    @FormUrlEncoded
    @POST("kabupaten/create.php")
    Call<Value> addKabkot(@Field("nm_kabupaten") String nm_kabupaten);

    @FormUrlEncoded
    @POST("kabupaten/edit.php")
    Call<Value> editKabkot(@Field("id_kabupaten") String id_kabupaten,
                           @Field("nm_kabupaten") String nm_kabupaten);

    @FormUrlEncoded
    @POST("kabupaten/delete.php")
    Call<Value> deleteKabkot(@Field("id_kabupaten") String id_kabupaten);

    @FormUrlEncoded
    @POST("kabupaten/search.php")
    Call<KabupatenResponse> searchKab(@Field("searchKab") String searchKab);
//    Kecamatan
    @GET("kecamatan/read.php")
    Call<KecamatanResponse> readKec(@Query("nm_kabupaten") String nm_kabupaten);

    @FormUrlEncoded
    @POST("kecamatan/create.php")
    Call<Value> addKec(@Field("nm_kabupaten") String nm_kabupaten,
                       @Field("nm_kecamatan") String nm_kecamatan);

    @FormUrlEncoded
    @POST("kecamatan/edit.php")
    Call<Value> editKec(@Field("id_kecamatan") String id_kecamatan,
                        @Field("nm_kabupaten") String nm_kabupaten,
                        @Field("nm_kecamatan") String nm_kecamatan);

    @FormUrlEncoded
    @POST("kecamatan/delete.php")
    Call<Value> deleteKec(@Field("id_kecamatan") String id_kecamatan);
//    SPINNER
    @GET("spinner/spinKabupaten.php")
    Call<KabupatenResponse> spinKab();

    @GET("spinner/spinKecamatan.php")
    Call<KecamatanResponse> spinKec(@Query("nm_kabupaten") String nm_kabupaten);

    @GET("spinner/spinKelurahan.php")
    Call<KelurahanResponse> spinKel(@Query("nm_kecamatan") String nm_kecamatan);

    @GET("spinner/spinLatLng.php")
    Call<LatlngResponse> spinLatlng(@Query("nm_kelurahan") String nm_kelurahan);

//    Kelurahan
    @GET("kelurahan/read.php")
    Call<KelurahanResponse> readKel(@Query("nm_kecamatan") String nm_kecamatan);

    @FormUrlEncoded
    @POST("kelurahan/create.php")
    Call<Value> addKel(@Field("nm_kabupaten") String nm_kabupaten,
                       @Field("nm_kecamatan") String nm_kecamatan,
                       @Field("nm_kelurahan") String nm_kelurahan);

    @FormUrlEncoded
    @POST("kelurahan/edit.php")
    Call<Value> editKel(@Field("id_kelurahan") String id_kelurahan,
                        @Field("nm_kabupaten") String nm_kabupaten,
                        @Field("nm_kecamatan") String nm_kecamatan,
                        @Field("nm_kelurahan") String nm_kelurahan);

    @FormUrlEncoded
    @POST("kelurahan/delete.php")
    Call<Value> deleteKel(@Field("id_kelurahan") String id_kelurahan);
//    Latitude Longtitude
    @GET("latlng/read.php")
    Call<LatlngResponse> readLatlng(@Query("nm_kelurahan") String nm_kelurahan);

    @FormUrlEncoded
    @POST("latlng/create.php")
    Call<Value> addLatlng(@Field("nm_kabupaten") String nm_kabupaten,
                          @Field("nm_kecamatan") String nm_kecamatan,
                          @Field("nm_kelurahan") String nm_kelurahan,
                          @Field("nm_lat") String nm_lat,
                          @Field("nm_lng") String nm_lng);

    @FormUrlEncoded
    @POST("latlng/edit.php")
    Call<Value> editLatlng(@Field("id_latlng") String id_latlng,
                           @Field("nm_kabupaten") String nm_kabupaten,
                           @Field("nm_kecamatan") String nm_kecamatan,
                           @Field("nm_kelurahan") String nm_kelurahan,
                           @Field("nm_lat") String nm_lat,
                           @Field("nm_lng") String nm_lng);

    @FormUrlEncoded
    @POST("latlng/delete.php")
    Call<Value> deleteLatlng(@Field("id_latlng") String id_latlng);

    @GET("latlng/detailLatlng.php")
    Call<LatlngResponse> detailLatlng(@Query("id_latlng") String id_latlng);
//    MAHASISWA
    @FormUrlEncoded
    @POST("mahasiswa/create.php")
    Call<Value> insertMhs(@Field("nim") String nim,
                          @Field("username") String username,
                          @Field("nama") String nama,
                          @Field("no_hp") String no_hp,
                          @Field("jk") String jk,
                          @Field("fakultas") String fakultas,
                          @Field("prodi") String prodi,
                          @Field("angkatan") String angkatan,
                          @Field("provinsi") String provinsi,
                          @Field("nm_kabupaten") String nm_kabupaten,
                          @Field("nm_kecamatan") String nm_kecamatan,
                          @Field("nm_kelurahan") String nm_kelurahan,
                          @Field("nm_lat") String nm_lat,
                          @Field("nm_lng") String nm_lng);

    @GET("mahasiswa/read.php")
    Call<MahasiswaResponse> readMhs(@Query("username") String username);

    @GET("mahasiswa/readAll.php")
    Call<MahasiswaResponse> readAllMhs();

    @GET("mahasiswa/readDetail.php")
    Call<MahasiswaResponse> readDetailMhs(@Query("nim") String nim);

    @GET("mahasiswa/showMap.php")
    Call<MahasiswaResponse> showMapMhs(@Query("nim") String nim);

    @FormUrlEncoded
    @POST("mahasiswa/edit.php")
    Call<Value> editMhs(@Field("nim") String nim,
                          @Field("username") String username,
                          @Field("nama") String nama,
                          @Field("no_hp") String no_hp,
                          @Field("jk") String jk,
                          @Field("fakultas") String fakultas,
                          @Field("prodi") String prodi,
                          @Field("angkatan") String angkatan,
                          @Field("provinsi") String provinsi,
                          @Field("nm_kabupaten") String nm_kabupaten,
                          @Field("nm_kecamatan") String nm_kecamatan,
                          @Field("nm_kelurahan") String nm_kelurahan,
                          @Field("nm_lat") String nm_lat,
                          @Field("nm_lng") String nm_lng);

    @FormUrlEncoded
    @POST("mahasiswa/delete.php")
    Call<Value> deleteMahasiswa(@Field("nim") String nim);

    @FormUrlEncoded
    @POST("mahasiswa/search.php")
    Call<MahasiswaResponse> searchMhs(@Field("searchMhs") String searchMhs);
//    DATA PADM
    @GET("data_padm/total.php")
    Call<PadmResponse> showPadm();
}
