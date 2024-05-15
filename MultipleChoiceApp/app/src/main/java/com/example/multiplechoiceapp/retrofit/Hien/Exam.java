package com.example.multiplechoiceapp.retrofit.Hien;

import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Exam {
    @GET("user/tested-assignment/{username}")
    Call<ResultResponse> getExam(
            @Path("username") String username
    );
}
