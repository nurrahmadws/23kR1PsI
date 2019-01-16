package com.example.matatabi.padm.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// http://myserverr.com/api/
// http://192.168.43.207/api/
public class RetrofitClient {
    private static final String base_url = "http://192.168.43.207/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit = null;

    private RetrofitClient(){
//        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
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
