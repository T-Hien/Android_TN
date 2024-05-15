package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateUser extends AppCompatActivity {
    private Button btnUpdateUser;
    private String username ="";
    private Long topicID = 0L;
    private EditText editName, edtPhone, edtEmail;
    private String name, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);
        setControl();
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("USERNAME");
            name = intent.getStringExtra("NAME");
            email = intent.getStringExtra("EMAIL");
            phone = intent.getStringExtra("PHONE");

        }
        setEvent();
    }

    private void setEvent() {
        editName.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(phone);
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();
                updateUser(username, name, email, phone, new CallbackMethod() {
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
                Intent intent = new Intent(UpdateUser.this, AccountUser.class);
                intent.putExtra("USERNAME",username);
                v.getContext().startActivity(intent);
            }
        });
    }
    private void updateUser(String username, String name, String email, String phone, CallbackMethod callbackMethod) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.updateUser(username, name, email, phone);
        call.enqueue(new Callback<ResultResponse>() {

            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                Toast.makeText(UpdateUser.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("onFailue","Erro: "+t.getMessage());
                Toast.makeText(UpdateUser.this, "onFailure:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setControl() {
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        editName = findViewById(R.id.edtNameUser);
        edtEmail = findViewById(R.id.edtMailUser);
        edtPhone = findViewById(R.id.edtPhoneUser);
    }

}