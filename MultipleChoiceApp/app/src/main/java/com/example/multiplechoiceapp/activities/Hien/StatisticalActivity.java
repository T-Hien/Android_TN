package com.example.multiplechoiceapp.activities.Hien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.retrofit.Hien.Statistic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatisticalActivity extends AppCompatActivity {

    List<Object> dataList;
    CustomAdapterStatistic customAdapterStatistic;

    ListView lvDanhSachThongKe;
    Button btnBD;

    Button btnC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        setControl();

        getStatistic("thuhien123", new CallbackMethod() {
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
        Button btnBD = findViewById(R.id.btnBD);
        btnBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticalActivity.this, ChartStatistic.class);
                startActivity(intent);
            }
        });
        Button btnC = findViewById(R.id.btnC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticalActivity.this, PDFStatistical.class);
                startActivity(intent);
            }
        });
    }

    private void setEvent() {
        customAdapterStatistic = new CustomAdapterStatistic(this, R.layout.layout_statistical, dataList);
        lvDanhSachThongKe.setAdapter(customAdapterStatistic);
    }

    private void getStatistic(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        Statistic statistic = retrofit.create(Statistic.class);
        Call<ResultResponse> call = statistic.getStatistic(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse.getStatus() == 200) {
                    dataList = resultResponse.getDataList();
                    callback.onSuccess(resultResponse.getMessage());
                }
                else {
                    callback.onFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private void setControl() {
        lvDanhSachThongKe = findViewById(R.id.lvDanhSachThongKe);
        btnBD = findViewById(R.id.btnBD);
        btnC = findViewById(R.id.btnC);

    }
}