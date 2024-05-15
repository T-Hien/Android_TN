package com.example.multiplechoiceapp.activities.TRIEU;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.adapters.NotificationAdapter;
import com.example.multiplechoiceapp.fragments.NavBottom;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.RecyclerViewMargin;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.ExperimentalBadgeUtils;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@ExperimentalBadgeUtils
public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;

    private Handler handler;
    private String username;
    private List<Notification> list = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Bắt đầu một FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment vào Activity
        NavBottom navBottom = new NavBottom(); // Khởi tạo Fragment
        fragmentTransaction.add(R.id.fragment_container, navBottom); // Thêm Fragment vào Activity
        fragmentTransaction.commit();

        initViews();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        ApiUtils.apiUserInterface().getNotification(username).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                RecyclerViewMargin decoration = new RecyclerViewMargin(20, response.body().size());
                recyclerView.addItemDecoration(decoration);
                notificationAdapter = new NotificationAdapter(response.body(),getApplicationContext());
                recyclerView.setAdapter(notificationAdapter);
            }
            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.d("data3", t.toString());
            }
        });

        handler = new Handler(Looper.getMainLooper());
        repeatTask();
    }

    private void repeatTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiUtils.apiUserInterface().getNotification(username).enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                        notificationAdapter = new NotificationAdapter(response.body(), getApplicationContext());
                        recyclerView.setAdapter(notificationAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {
                        Log.d("data3", t.toString());
                    }
                });

                // Gọi lại hàm repeatTask() để lặp lại sau mỗi 3 giây
                repeatTask();
            }
        }, 3000); // Thực hiện sau mỗi 3 giây
    }

    private void initViews() {
        setUpToolbar();
    }

    private void setUpToolbar() {
        badgeDrawable = BadgeDrawable.create(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.notification){
            Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}