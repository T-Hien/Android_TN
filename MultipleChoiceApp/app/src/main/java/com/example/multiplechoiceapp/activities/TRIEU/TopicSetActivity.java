package com.example.multiplechoiceapp.activities.TRIEU;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.DTO.SharedTopicSetResponse;
import com.example.multiplechoiceapp.R;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import android.Manifest;

import com.example.multiplechoiceapp.adapters.TopicSetAdapter;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.Count;
import com.example.multiplechoiceapp.utils.RecyclerViewMargin;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;


@ExperimentalBadgeUtils
public class TopicSetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TopicSetAdapter topicAdapter;

    private SearchView searchView;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_set);
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        setControl();
        initData();
    }

    private void setControl(){
        recyclerView = findViewById(R.id.recyclerView);

    }

    private void initData(){

        ApiUtils.apiTopicInterface().getTopicSetsOfShareTopic(username).enqueue(new Callback<List<SharedTopicSetResponse>>() {
            @Override
            public void onResponse(Call<List<SharedTopicSetResponse>> call, Response<List<SharedTopicSetResponse>> response) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                topicAdapter = new TopicSetAdapter(response.body(),getApplicationContext());
                recyclerView.setAdapter(topicAdapter);
                topicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SharedTopicSetResponse>> call, Throwable t) {
                Log.d("data3", t.toString());
            }
        });
    }


}