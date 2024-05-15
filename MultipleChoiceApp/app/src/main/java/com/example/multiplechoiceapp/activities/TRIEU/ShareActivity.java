package com.example.multiplechoiceapp.activities.TRIEU;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.DTO.User.Username;
import com.example.multiplechoiceapp.DTO.User.UsernameList;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.THien.TopicSet;
import com.example.multiplechoiceapp.adapters.UserAdapter;
import com.example.multiplechoiceapp.fragments.NavBottom;
import com.example.multiplechoiceapp.models.User;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.ClickListener;
import com.example.multiplechoiceapp.utils.Count;
import com.example.multiplechoiceapp.utils.RecyclerViewMargin;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ExperimentalBadgeUtils
public class ShareActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private SearchView searchView;
    private List<User> userList = Collections.emptyList();
    private List<Username> usernameList = new ArrayList<>();
    private Long topicSetCode, topicID;
    private Button btnConfirm;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        topicSetCode = intent.getLongExtra("topicSetCode", 0);
        topicID = intent.getLongExtra("topicID", 0);
        initData();
        setUpSearchView();

    }

    private void initData(){
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiUtils.apiUserInterface().getFriendsOfUser(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
                RecyclerViewMargin decoration = new RecyclerViewMargin(20, userList.size());
                recyclerView.addItemDecoration(decoration);
                userAdapter = new UserAdapter(new ClickListener() {
                    @Override
                    public void onPositionClicked(int position, int status) {
                        if(status == 0){
                            usernameList.add(new Username(userList.get(position).getUsername()));
                        } else if(status == 4){
                            Username  username1 = usernameList.stream().findAny().filter(username -> username.getUsername() == userList.get(position).getUsername()).orElse(null);
                            boolean check = usernameList.remove(username1);
                        }

                        if(usernameList.size() != 0){
                            btnConfirm.setEnabled(true);
                        } else {
                            btnConfirm.setEnabled(false);
                        }
                    }

                    @Override
                    public void onLongClicked(int position) {

                    }
                }, response.body(), getApplicationContext());
                recyclerView.setAdapter(userAdapter);
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });

    }

    private void setUpSearchView(){
        this.searchView = (SearchView) findViewById(R.id.searchViewUser);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public  void onConfirm(View view){
        if(view.getId() == btnConfirm.getId()){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Thông báo");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Đồng ý",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ApiUtils.apiTopicInterface().shareTopicSetToUsers(topicID,topicSetCode, username,new UsernameList(usernameList))
                                    .enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"Chia sẽ thành công",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), TopicSet.class);
                                            intent.putExtra("ID_TOPIC", topicID);
                                            intent.putExtra("USERNAME", username);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Log.d("ErrorFromServer",t.toString());
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
    }
}