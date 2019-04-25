package com.example.user.training;

public class ApiUtils {
    private static final String baseUrl = "https://rawgit.com/";
    public static Api getApi(){
        return RetrofitClient.getRetrofit(baseUrl).create(Api.class);
    }
}
