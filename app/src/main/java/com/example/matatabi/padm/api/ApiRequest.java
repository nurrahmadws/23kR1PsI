package com.example.matatabi.padm.api;

import com.example.matatabi.padm.model.KabupatenResponse;
import com.example.matatabi.padm.model.KecamatanResponse;
import com.example.matatabi.padm.model.LoginResponse;
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
    @POST("users/delete.php")
    Call<Value> deleteUser(@Field("id_user") String id_user);
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
}
