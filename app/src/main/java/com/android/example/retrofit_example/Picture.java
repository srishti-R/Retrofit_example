package com.android.example.retrofit_example;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

import retrofit2.http.Url;


public class Picture {
    private String title;
    private URL url;
    @SerializedName("explanation")
    private String body;

    private String date;

    public Picture(String title, URL url, String body, String dates) {
        this.title = title;
        this.url = url;
        this.body = body;
        date=dates;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public URL getImageUrl() {
        return url ;
    }

    public String getBody() {
        return body;
    }
}
