package com.example.multiplechoiceapp.activities.QUY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText edtUsername, edtPhoneNumber, edtPassword, edtReEnterPW;
    private Button btnVerify;

    private TextView tvSignIn, tvRegister;
    private String username, password, phoneNumber;
    private String countryCode = "+84";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setControl();
    }

    public void onVerify(View v) {
        if (v.getId() == R.id.btnVerify) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            String reEnterPW = edtReEnterPW.getText().toString();
            phoneNumber = edtPhoneNumber.getText().toString();

            if(username.trim().equals("")) {
                Toast.makeText(ForgotPassword.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                edtUsername.setError("Nhập tài khoản");
                edtUsername.requestFocus();
                return;
            }

            if(password.trim().equals("")) {
                Toast.makeText(ForgotPassword.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                edtPassword.setError("Nhập mật khẩu");
                edtPassword.requestFocus();
                return;
            }

            if(reEnterPW.trim().equals("")) {
                Toast.makeText(ForgotPassword.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                edtReEnterPW.setError("Nhập lại mật khẩu");
                edtReEnterPW.requestFocus();
                return;
            }

            if(phoneNumber.trim().equals("")) {
                Toast.makeText(ForgotPassword.this, "Vui lòng nhập SĐT", Toast.LENGTH_SHORT).show();
                edtPhoneNumber.setError("Nhập SĐT");
                edtPhoneNumber.requestFocus();
                return;
            }

            if (!password.equals(reEnterPW)) {
                Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không chính xác!", Toast.LENGTH_SHORT).show();
                edtReEnterPW.setError("Nhập lại mật khẩu");
                edtReEnterPW.requestFocus();
                return;
            }

            getUserByUsername(username, new CallbackMethod() {
                @Override
                public void onSuccess(String information) {

                }

                @Override
                public void onSuccess(String username_, String phoneNumber) {
                    if (username.equals(username_) && phoneNumber.equals(phoneNumber)) {
                        sendVerificationCode(countryCode + phoneNumber);
                    }
                }

                @Override
                public void onFailure(String errorMessage) {

                }
            });
        }
    }

    public void getUserByUsername(String username, CallbackMethod callback) {
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
                    User user = gson.fromJson(resultResponse.getData().toString(), User.class);
                    String username_ = user.getUsername();
                    String phoneNumber_ = user.getPhoneNumber();
                    callback.onSuccess(username_, phoneNumber_);
                } else {
                    Toast.makeText(getApplicationContext(), resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure("Có lỗi đã xảy ra!");
            }
        });
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
            otpVerification.putExtra("flag", "ResetPW");
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
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public void onConvertSignIn(View v) {
        if (v.getId() == R.id.tvSignIn) {
            Intent signInView = new Intent(this, Login.class);
            signInView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signInView);
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
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtReEnterPW = findViewById(R.id.edtReEnterPW);

        btnVerify = findViewById(R.id.btnVerify);

        tvSignIn = findViewById(R.id.tvSignIn);
        tvRegister = findViewById(R.id.tvRegister);
    }
}