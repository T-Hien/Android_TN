package com.example.multiplechoiceapp.retrofit.Hien;

import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Topic {
    @FormUrlEncoded
    @POST("topic/{username}/create")
    Call<ResultResponse> addTopic(
            @Path("username") String username,
            @Field("name") String name
    );
}
