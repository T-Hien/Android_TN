package com.example.multiplechoiceapp.retrofit.Hien;



import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Statistic {
    @GET("user/statistic/{username}")
    Call<ResultResponse> getStatistic(
            @Path("username") String username
    );

    @GET("user/statistic/topic-set/{topic_set_code}")
    Call<ResultResponse> getTopicSetStatistic(
            @Path("topic_set_code") String topic_set_code
    );
}
