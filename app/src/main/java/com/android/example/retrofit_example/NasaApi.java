package com.android.example.retrofit_example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("apod?api_key=BM2lQ99gjhQqkujoOBBeFdD26LXgtg5y9lXR5dKW")
    Call<Picture> getPicture(@Query("date") String date);
}
