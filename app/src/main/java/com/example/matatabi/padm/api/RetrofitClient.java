package com.example.matatabi.padm.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String base_url = "http://192.168.43.207/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
//        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getmInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiRequest getApi(){
        return retrofit.create(ApiRequest.class);
    }
}
