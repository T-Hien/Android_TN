package com.example.multiplechoiceapp.retrofit.api;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiUserInterface {

    @GET("user/{userId}/notification")
    Call<List<Notification>> getNotification(@Path("userId") String userId);
    @GET("user/{userId}/friends")
    Call<List<User>> getFriendsOfUser(@Path("userId") String userId);
}
