package com.example.multiplechoiceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Hien.StatisticalActivity;
import com.example.multiplechoiceapp.activities.THien.TopicSet;
import com.example.multiplechoiceapp.activities.THien.Topic_Home;
import com.example.multiplechoiceapp.activities.THien.addTopic;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.CustomAdapterTopic;
import com.example.multiplechoiceapp.models.Topic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private String username;
    private Button btnShareTopicSetHome, btnStatisticalHome, btnTopicHome,btnAddHome;
    private RecyclerView lvHome;
    private CustomAdapterTopic customAdapterTopic;
    private Long topicID = 0L;
    private float duration = 0F;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl(view);
        setEvent();
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        TakeTopic(retrofitClient,username);
        btnTopicHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Topic_Home.class);
                intent.putExtra("ID_TOPIC", topicID);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        btnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), addTopic.class);
                startActivity(intent);
            }
        });

        btnShareTopicSetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TopicSetActivity.class);
                startActivity(intent);
            }
        });

        btnStatisticalHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StatisticalActivity.class);
                startActivity(intent);
            }
        });
        lvHome.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        if (position != RecyclerView.NO_POSITION) {
                            if (position < customAdapterTopic.getItemCount()) {
                                Topic tp = customAdapterTopic.getItem(position);
                                topicID = tp.getTopicCode();
                                String name = tp.getTopicName();
                                if (tp != null) {
                                    Intent intent = new Intent(getContext(), TopicSet.class);
                                    intent.putExtra("ID_TOPIC", topicID);
                                    intent.putExtra("USERNAME", username);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

    }

    private void TakeTopic(RetrofitClient retrofitClient, String username) {
        retrofitClient.getTopicHome(username, new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    List<Topic> topic = response.body();
                    lvHome.setLayoutManager(new GridLayoutManager(getContext(), 4, RecyclerView.VERTICAL, false));
                    customAdapterTopic = new CustomAdapterTopic(getContext(), R.layout.layout_list_topic_home, topic);
                    lvHome.setAdapter(customAdapterTopic);
                } else {
                    Log.e("Erro", "Lỗi Topic");

                }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Log.e("E",t.getMessage());

            }
        });
    }

    private void setControl(View view) {
        btnShareTopicSetHome = view.findViewById(R.id.btnShareTopicSetHome);
        btnStatisticalHome = view.findViewById(R.id.btnStatisticalHome);
        lvHome = view.findViewById(R.id.lvHome);
        btnTopicHome = view.findViewById(R.id.btnTopicHome);
        btnAddHome = view.findViewById(R.id.btnAddHome);
    }

}