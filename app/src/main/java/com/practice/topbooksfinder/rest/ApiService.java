package com.practice.topbooksfinder.rest;

import com.practice.topbooksfinder.model.BookInfo;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.model.ListInfoResponse;
import com.practice.topbooksfinder.model.SimpleResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    final String apiKey = "mgSFb63JH0NaiSADfxg4Oa3aOgkWVALb";

    @GET("lists/best-sellers/history.json?api-key=" + apiKey)
    Call<SimpleResponseModel<BookInfo>> getBook(@QueryMap Map<String, String> options);

    @GET("lists/names.json?api-key=" + apiKey)
    Call<SimpleResponseModel<ListInfo>> getLists();

    @GET("lists.json?api-key=" + apiKey)
    Call<SimpleResponseModel<ListInfo>> getList(@Query("list") String listName);

    @GET("lists/{date}/{list}.json?api-key=" + apiKey)
    Call<ListInfoResponse> getListByDate(@Path("list") String listName, @Path("date") String date);
}
