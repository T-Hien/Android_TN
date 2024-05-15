package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountUser extends AppCompatActivity {
    private Button btnExitAccount, btnUpdateAccount;
    private String username ="";
    private Long topicID = 0L;
    private TextView txtPhoneUser, txtMailUser, txtNameUser;
    private String name, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountuser);
        setControl();
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("USERNAME");

        }
        setEvent();
    }

    private void setEvent() {
        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountUser.this, UpdateUser.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("NAME",name);
                intent.putExtra("EMAIL",email);
                intent.putExtra("PHONE",phone);
                v.getContext().startActivity(intent);
            }
        });
        btnExitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountUser.this, MainRenovationActivity.class);
                intent.putExtra("USERNAME",username);
                v.getContext().startActivity(intent);
            }
        });
        RetrofitClient retrofitClient = new RetrofitClient();
        getUser(username, new CallbackMethod() {
            @Override
            public void onSuccess(String information) {
            }

            @Override
            public void onSuccess(String a, String b) {
            }

            @Override
            public void onFailure(String errorMessage) {
            }
        });
    }

    private void getUser(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);

        Call<ResultResponse> call = authenticationService.getUser(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse != null && resultResponse.getStatus() == 200) {
                    String dataString = resultResponse.getData().toString();
                    dataString = dataString.substring(0, dataString.lastIndexOf("}"));
                    String[] parts = dataString.split(", ");

                    int i =1;
                    for (String part : parts) {
                        String[] keyValue = part.split("=");

                        if (keyValue.length == 2) {
                            String key = keyValue[0];
                            String value = keyValue[1];

                            if(i==3){
                                name = value;
                                txtNameUser.setText(value);
                            }
                            if(i==6){
                                email = value;
                                txtMailUser.setText(value);
                            }
                            if(i==7){
                                phone = value;
                                txtPhoneUser.setText(value);
                            }
                            i +=1;
                        }
                    }

                }
                else{
                    Log.e("UnSucc",response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

    }

    private void setControl() {
        btnExitAccount = findViewById(R.id.ExitAccount);
        btnUpdateAccount = findViewById(R.id.UpdateAccount);
        txtPhoneUser = findViewById(R.id.PhoneUser);
        txtMailUser = findViewById(R.id.MailUser);
        txtNameUser = findViewById(R.id.NameUser);
    }

}