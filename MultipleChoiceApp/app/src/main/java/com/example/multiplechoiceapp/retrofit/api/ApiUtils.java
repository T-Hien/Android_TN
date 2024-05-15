package com.example.multiplechoiceapp.retrofit.api;


import com.example.multiplechoiceapp.retrofit.RetrofitClient;

public class ApiUtils {
    public static ApiUserInterface apiUserInterface(){
        return RetrofitClient.getClient().create(ApiUserInterface.class);
    }

    public static ApiTopicInterface apiTopicInterface(){
        return RetrofitClient.getClient().create(ApiTopicInterface.class);
    }
}
