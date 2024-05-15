package com.example.multiplechoiceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Hien.ChartPoint;
import com.example.multiplechoiceapp.activities.Hien.CustomAdapterExam;
import com.example.multiplechoiceapp.activities.Hien.MainActivity;
import com.example.multiplechoiceapp.activities.Hien.PDFPoint;
import com.example.multiplechoiceapp.activities.THien.LevelQuestion;
import com.example.multiplechoiceapp.retrofit.Hien.Exam;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatisticalFragment extends Fragment {
    List<Object> dataList = new ArrayList<>();
    CustomAdapterExam customAdapterExam;
    Button btnChart1;
    Button btnCreate;

    ListView lvDanhSachDaThi;
    private String username;
    private Long topicSetID = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistical, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();

        RetrofitClient retrofitClient = new RetrofitClient();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        getExam(username, new CallbackMethod() {
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


        Button btnChart1 = view.findViewById(R.id.btnChart1);
        btnChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChartPoint.class);
                startActivity(intent);
            }
        });
        Button btnCreate = view.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PDFPoint.class);
                startActivity(intent);
            }
        });
    }

    private void setEvent() {
        customAdapterExam = new CustomAdapterExam(getContext(), R.layout.layout_exam, dataList);
        lvDanhSachDaThi.setAdapter(customAdapterExam);
        customAdapterExam.setOnItemClickListener(new CustomAdapterExam.OnItemClickListener() {
            @Override
            public void onItemClick(String maCD) {
                // Xử lý khi item được click
                Toast.makeText(getContext(), "Mã CD: " + maCD + " được chọn", Toast.LENGTH_SHORT).show();
                topicSetID = Long.parseLong(maCD);
                Intent intent = new Intent(getContext(), LevelQuestion.class);
                intent.putExtra("ID_TOPICSET",topicSetID);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }


    private void getExam(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        Exam exam = retrofit.create(Exam.class);
        Call<ResultResponse> call = exam.getExam(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse.getStatus() == 200) {
                    dataList = resultResponse.getDataList();
                    callback.onSuccess(resultResponse.getMessage());
                } else {
                    callback.onFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private void setControl(View view) {
        lvDanhSachDaThi = view.findViewById(R.id.lvDanhSachDaThi);
        btnChart1 = view.findViewById(R.id.btnChart1);
        btnCreate = view.findViewById(R.id.btnCreate);
    }
}