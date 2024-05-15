package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.adapters.CustomAdapterTopic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topic_Home extends AppCompatActivity {
    private RecyclerView lvTopic;
    private CustomAdapterTopic customAdapterTopic;
    private String username ="";
    private Long topicID = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        setControl();
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("USERNAME");
        }
        setEvent();
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        getTopic(retrofitClient,username);
        lvTopic.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        if (position != RecyclerView.NO_POSITION) {
                            if (position < customAdapterTopic.getItemCount()) {
                                com.example.multiplechoiceapp.models.Topic tp = customAdapterTopic.getItem(position);
                                topicID = tp.getTopicCode();
                                String name = tp.getTopicName();
                                if (tp != null) {
                                    Intent intent = new Intent(Topic_Home.this,TopicSet.class);
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

    private void getTopic(RetrofitClient retrofitClient, String username) {
        retrofitClient.getTopicHome(username, new Callback<List<com.example.multiplechoiceapp.models.Topic>>() {
            @Override
            public void onResponse(Call<List<com.example.multiplechoiceapp.models.Topic>> call, Response<List<com.example.multiplechoiceapp.models.Topic>> response) {
                if (response.isSuccessful()) {
                    List<com.example.multiplechoiceapp.models.Topic> topic = response.body();
                    lvTopic.setLayoutManager(new GridLayoutManager(Topic_Home.this, 4, RecyclerView.VERTICAL, false));
                    customAdapterTopic = new CustomAdapterTopic(Topic_Home.this, R.layout.layout_list_topic_home, topic);
                    lvTopic.setAdapter(customAdapterTopic);
                } else {
                    Log.e("Erro onResponse", "getTopic");

                }
            }

            @Override
            public void onFailure(Call<List<com.example.multiplechoiceapp.models.Topic>> call, Throwable t) {
                Log.e("Erro onFailure","getTopic:"+t.getMessage());

            }
        });
    }

    private void setControl() {
        lvTopic = findViewById(R.id.lvTopic);
    }

}