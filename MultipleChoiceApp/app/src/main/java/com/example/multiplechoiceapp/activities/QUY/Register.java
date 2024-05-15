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
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText edtUsername, edtPassword, edtReEnterPW, edtPhoneNumber;
    private Button btnSignUp;
    private TextView tvSignIn;
    private String username, password, phoneNumber;
    private String countryCode = "+84";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setControl();
    }

    public void onSignUp(View v) {
        if (v.getId() == R.id.btnSignUp) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            String reEnterPW = edtReEnterPW.getText().toString();
            phoneNumber = edtPhoneNumber.getText().toString();

            if(username.trim().equals("")) {
                Toast.makeText(Register.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                edtUsername.setError("Nhập tài khoản");
                edtUsername.requestFocus();
                return;
            }

            if(password.trim().equals("")) {
                Toast.makeText(Register.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                edtPassword.setError("Nhập mật khẩu");
                edtPassword.requestFocus();
                return;
            }

            if(reEnterPW.trim().equals("")) {
                Toast.makeText(Register.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                edtReEnterPW.setError("Nhập lại mật khẩu");
                edtReEnterPW.requestFocus();
                return;
            }

            if(phoneNumber.trim().equals("")) {
                Toast.makeText(Register.this, "Vui lòng nhập SĐT", Toast.LENGTH_SHORT).show();
                edtPhoneNumber.setError("Nhập SĐT");
                edtPhoneNumber.requestFocus();
                return;
            }

            if(phoneNumber.trim().length() != 10) {
                Toast.makeText(Register.this, "Vui lòng kiểm tra lại SĐT", Toast.LENGTH_SHORT).show();
                edtPhoneNumber.setError("Nhập lại SĐT");
                edtPhoneNumber.requestFocus();
                return;
            }

            if (!password.equals(reEnterPW)) {
                Toast.makeText(Register.this, "Mật khẩu nhập lại không chính xác!", Toast.LENGTH_SHORT).show();
                edtReEnterPW.setError("Nhập lại mật khẩu");
                edtReEnterPW.requestFocus();
                return;
            }
            checkExistUser(username, new CallbackMethod() {
                @Override
                public void onSuccess(String information) {
                    Toast.makeText(getApplicationContext(), information, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(String a, String b) {

                }

                @Override
                public void onFailure(String errorMessage) {
                    sendVerificationCode(countryCode + phoneNumber);
                }
            });
        }
    }

    private void checkExistUser(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);

        Call<ResultResponse> call = authenticationService.getUser(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();

                if (resultResponse.getStatus() == 200) {
                    callback.onSuccess("Tài khoản này đã tồn tại");
                } else {
                    callback.onFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure("1231231231");
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
            Intent otpVerification = new Intent(Register.this, OTPVerification.class);
            otpVerification.putExtra("flag", "SignUp");
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
            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    public void onConvertSignIn(View v) {
        if (v.getId() == R.id.tvSignIn) {
            Intent signInView = new Intent(this, Login.class);
            signInView.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signInView);
        }
    }

    private void setControl() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtReEnterPW = findViewById(R.id.edtReEnterPW);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        btnSignUp = findViewById(R.id.btnSignUp);

        tvSignIn = findViewById(R.id.tvSignIn);
    }
}