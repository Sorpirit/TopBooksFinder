package com.practice.topbooksfinder.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


    private static RestClient client = new RestClient();

    private ApiService service;
    private Retrofit retrofit;
    private Gson gson;

    private final static String BASE_URL = "https://api.nytimes.com/svc/books/v3/";

    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        gson = new GsonBuilder()
                //.registerTypeAdapter(Uri.class, new UriDeserializer())
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(ApiService.class);
    }

    public static RestClient getClient() {
        return client;
    }

    public ApiService getService() {
        return service;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }
}
