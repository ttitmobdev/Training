package com.example.user.training;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/startandroid/data/master/messages/messages1.json")
    Call<List<JSONResponse>> getData();
}
