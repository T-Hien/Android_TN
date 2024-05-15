package com.example.multiplechoiceapp.activities.THien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.QUY.Login;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.example.multiplechoiceapp.models.User;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.Hien.Topic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class addTopic extends AppCompatActivity {

    private Button btnAddTopic, btnExitTopic;
    private EditText edtTopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtopic);
        setEvent();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitClient.getClient();
                Topic topic = retrofit.create(Topic.class);
                Call<ResultResponse> call = topic.addTopic(username, edtTopicName.getText().toString());
                call.enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        ResultResponse resultResponse = response.body();

                        if (resultResponse.getStatus() == 200) {
                            Toast.makeText(addTopic.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(addTopic.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(addTopic.this, MainRenovationActivity.class);
                        getApplicationContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        Toast.makeText(addTopic.this, "Đã có lỗi xảy ra! Vui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(addTopic.this, MainRenovationActivity.class);
                        getApplicationContext().startActivity(intent);
                    }
                });
            }
        });
        btnExitTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addTopic.this, MainRenovationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

   void setEvent(){
       btnAddTopic = findViewById(R.id.btnAddTopic);
       btnExitTopic = findViewById(R.id.btnExitTopic);
       edtTopicName = findViewById(R.id.edtTopicName);
    }
}