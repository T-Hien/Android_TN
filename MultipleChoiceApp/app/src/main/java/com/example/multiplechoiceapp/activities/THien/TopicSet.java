package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Minh.CreateTopicSetActivity;
import com.example.multiplechoiceapp.activities.TRIEU.NotificationActivity;
import com.example.multiplechoiceapp.activities.TRIEU.ShareActivity;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.CustomAdapterTopicSet;
import com.example.multiplechoiceapp.fragments.NavBottom;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.Count;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@ExperimentalBadgeUtils
public class TopicSet extends AppCompatActivity {

    private Button btnthi, btnfindTopicSet,btnAddTopicSet;

    private ListView lvDanhsach;
    private CustomAdapterTopicSet customAdapterTopicSet;
    private Float duration = 0F;
    private Long topicSetCode =0L;
    private String username ="";
    private Long topicID=0L;

    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;

    private Handler handler;
    private int tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topicset);


        Intent intent = getIntent();
        if (intent != null) {
            topicID = intent.getLongExtra("ID_TOPIC",0);
            username = intent.getStringExtra("USERNAME");
        }
        setControl();
        setEvent();
        btnAddTopicSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateTopicSetActivity.class);
                intent.putExtra("ID_TOPIC", topicID);
                startActivity(intent);
            }
        });
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        TakeTopicSet(retrofitClient);
    }

    public void TakeTopicSet(RetrofitClient retrofitClient) {
        retrofitClient.getTopicSetByTopicID(topicID, new Callback<List<Topic_Set>>() {
            @Override
            public void onResponse(Call<List<Topic_Set>> call, Response<List<Topic_Set>> response) {
                if(response.isSuccessful()){
                    List<Topic_Set> topicset = response.body();
                    for(Topic_Set tp: topicset){
                        topicSetCode = tp.getTopicSetID();
                    }
                    customAdapterTopicSet = new CustomAdapterTopicSet(TopicSet.this, R.layout.layout_list_topicset, topicset);
                    lvDanhsach.setAdapter(customAdapterTopicSet);
                    customAdapterTopicSet.setOnButtonClickListener(new CustomAdapterTopicSet.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(Topic_Set topicSet, int i) {
                            if(i == 1){
                                duration = topicSet.getDuration();
                                String time = String.valueOf(duration);
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, LevelQuestion.class);
                                intent.putExtra("ID_TOPICSET",topicSetCode);
                                intent.putExtra("USERNAME", username);
                                intent.putExtra("DURATION", duration);
                                startActivity(intent);
                            }else if (i == 2){
                                duration = topicSet.getDuration();
                                String time = String.valueOf(duration);
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, ExamAgain.class);
                                intent.putExtra("ID_TOPICSET", topicSetCode);
                                intent.putExtra("USERNAME",username);
                                intent.putExtra("DURATION",duration);
                                startActivity(intent);
                            } else if(i == 3) {
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, ShareActivity.class);
                                intent.putExtra("topicSetCode", topicSetCode);
                                intent.putExtra("topicID", topicID);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    Log.e("Error onResponse", "customAdapterTopicSet:"+response.message());

                }

            }

            @Override
            public void onFailure(Call<List<Topic_Set>> call, Throwable t) {
                Log.e("Error onFailure", "getTopic"+t.getMessage());
            }
        });
    }

    private void setControl(){
        lvDanhsach = findViewById(R.id.lvDanhsachTopicset);
        btnAddTopicSet = findViewById(R.id.btnAddTopicSet);
    }

}

