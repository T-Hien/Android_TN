package com.example.multiplechoiceapp.activities.Hien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.example.multiplechoiceapp.retrofit.Hien.Statistic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatingsActivity extends AppCompatActivity {
    List<Object> dataList;
    CustomAdapterRating customAdapterRating;
    ListView lvDanhSachXepHang;

    Button btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical1);
        setControl();

        Intent intent = getIntent();
        if (intent != null) {
            String maDe = intent.getStringExtra("MaDe");
            if (maDe != null) {
                getTopicSetStatistic(maDe, new CallbackMethod() {
                    @Override
                    public void onSuccess(String information) {
                        setEvent();
                    }

                    @Override
                    public void onSuccess(String a, String b) {
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                    }
                });
            }
        }
    }

    private void setEvent() {
        customAdapterRating = new CustomAdapterRating(this, R.layout.layout_ratings, dataList);
        lvDanhSachXepHang.setAdapter(customAdapterRating);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang giao diện của Activity_Home
                Intent intent = new Intent(RatingsActivity.this, MainRenovationActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getTopicSetStatistic(String topic_set_code, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        Statistic statistic = retrofit.create(Statistic.class);
        Call<ResultResponse> call = statistic.getTopicSetStatistic(topic_set_code);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResultResponse resultResponse = response.body();
                    if (resultResponse.getStatus() == 200) {
                        dataList = resultResponse.getDataList();
                        callback.onSuccess(resultResponse.getMessage());
                    } else {
                        callback.onFailure(resultResponse.getMessage());
                    }
                } else {
                    callback.onFailure("Failed to get response from server");
                }
            }
            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
    private void setControl() {
        lvDanhSachXepHang = findViewById(R.id.lvDanhSachXepHang);
        btnThoat = findViewById(R.id.btnThoat);
    }
}
