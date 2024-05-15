package com.example.multiplechoiceapp.activities.QUY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.models.User;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;
import com.example.multiplechoiceapp.retrofit.utils.DateDeserializer;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText edtUsername, edtPassword;
    private Button btnSignIn;
    private TextView tvForgotPW, tvRegister;
    private String username, password, phoneNumber;
    private String countryCode = "+84";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setControl();
    }

    public void onSignIn(View v) {
        if (v.getId() == R.id.btnSignIn)
        {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();

            if(username.trim().equals("")) {
                Toast.makeText(Login.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                edtUsername.setError("Nhập tài khoản");
                edtUsername.requestFocus();
                return;
            }

            if(password.trim().equals("")) {
                Toast.makeText(Login.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                edtPassword.setError("Nhập mật khẩu");
                edtPassword.requestFocus();
                return;
            }
            signIn(username, password, new CallbackMethod() {
                @Override
                public void onSuccess(String information) {
                    getPhoneNumberByUsername(username, new CallbackMethod() {
                        @Override
                        public void onSuccess(String phoneNumber) {
                            sendVerificationCode(countryCode + phoneNumber);
                        }

                        @Override
                        public void onSuccess(String a, String b) {

                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(Login.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onSuccess(String a, String b) {

                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(Login.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String otp, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Intent otpVerification = new Intent(getApplicationContext(), OTPVerification.class);
            otpVerification.putExtra("flag", "SignIn");
            otpVerification.putExtra("username", username);
            otpVerification.putExtra("password", password);
            otpVerification.putExtra("countryCode", countryCode);
            otpVerification.putExtra("phoneNumber", phoneNumber);
            otpVerification.putExtra("backendOTP", otp);
            otpVerification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(otpVerification);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public void getPhoneNumberByUsername(String username, CallbackMethod callback) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
                .create();

        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.getUser(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();

                if (resultResponse.getStatus() == 200) {
                    User user = gson.fromJson(resultResponse.getData().toString().trim(), User.class);
                    phoneNumber = user.getPhoneNumber();
                    callback.onSuccess(phoneNumber);
                } else {
                    Toast.makeText(Login.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure("Có lỗi đã xảy ra!");
            }
        });
    }

    private void signIn(String username, String password, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.signIn(username, password);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse.getStatus() == 200) {
                    callback.onSuccess(resultResponse.getMessage());
                }
                else {
                    callback.onFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure("Có lỗi đã xảy ra!");
            }
        });
    }

    public void onConvertForgotPW(View v) {
        if (v.getId() == R.id.tvForgotPW) {
            Intent forgotPasswordView = new Intent(this, ForgotPassword.class);
            forgotPasswordView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(forgotPasswordView);
        }
    }

    public void onConvertSignUp(View v) {
        if (v.getId() == R.id.tvRegister) {
            Intent signUpView = new Intent(this, Register.class);
            signUpView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signUpView);
        }
    }

    private void setControl() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        tvForgotPW = findViewById(R.id.tvForgotPW);
        tvRegister = findViewById(R.id.tvRegister);
    }
}