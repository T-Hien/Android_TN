package com.example.multiplechoiceapp.activities.Minh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.DTO.QuestionRequest;
import com.example.multiplechoiceapp.DTO.SelectionRequest;
import com.example.multiplechoiceapp.DTO.TopicSetRequest;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.THien.TopicSet;
import com.example.multiplechoiceapp.activities.TRIEU.NotificationActivity;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.QuestionAdapter;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.ClickListener;
import com.example.multiplechoiceapp.utils.Count;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ExperimentalBadgeUtils
public class CreateTopicSetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private Button buttonAddQuestion, buttonSubmitQuestion;
    private List<QuestionRequest> questions = new ArrayList<>();
    private EditText textTopicSetId,textTopicSetName,textTopicSetTime;
    private String username ="";
    private Long topicID;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_set);
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        Intent intent = getIntent();
        topicID = intent.getLongExtra("ID_TOPIC", 0);
        setControl();
        initData();
        setEvent();
    }

    private void initData(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void setControl() {
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        buttonSubmitQuestion = (Button) findViewById(R.id.buttonSubmitQuestion);
        textTopicSetName = (EditText) findViewById(R.id.textTopicSetName);
        textTopicSetTime = (EditText) findViewById(R.id.textTopicSetTime);
    }

    private void setEvent(){
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionRequest questionRequest = new QuestionRequest();

                List<SelectionRequest> selectionRequests = new ArrayList<>();
                for (int i = 0; i < 4; i++){
                    SelectionRequest selectionRequest = new SelectionRequest();
                    selectionRequests.add(selectionRequest);
                }
                questionRequest.setSelection(selectionRequests);
                questions.add(questionRequest);
                questionAdapter = new QuestionAdapter(new ClickListener() {
                    @Override
                    public void onPositionClicked(int position, int status) {

                    }

                    @Override
                    public void onLongClicked(int position) {

                    }
                }, questions, getApplicationContext());
                recyclerView.setAdapter(questionAdapter);
                questionAdapter.notifyDataSetChanged();

                layoutManager.scrollToPosition(questions.size());
                layoutManager.smoothScrollToPosition(recyclerView, null, questions.size());
            }
        });

        buttonSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Thông báo");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                                List<QuestionRequest> questionRequests = new ArrayList<>();
                                if(adapter instanceof QuestionAdapter){
                                    QuestionAdapter questionAdapter = (QuestionAdapter) adapter;
                                    questionRequests = questionAdapter.list;
                                }

                                String name = textTopicSetName.getText().toString();
                                float duration = Float.parseFloat(textTopicSetTime.getText().toString());
                                TopicSetRequest topicSetRequest = new TopicSetRequest(name,duration,questionRequests);
                                ApiUtils.apiTopicInterface().createTopicSet(1l,topicSetRequest).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(getApplicationContext(),"Tạo bộ đề thành công",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), TopicSet.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }



}