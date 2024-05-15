package com.example.multiplechoiceapp.activities.QUY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTPVerification extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String backendOTP, userInputOTP;
    private EditText edtOTP1, edtOTP2, edtOTP3, edtOTP4, edtOTP5, edtOTP6;
    private TextView tvShowPhoneNumber, tvResendOTP;
    private Button btnVerify;
    private String flag, username, password, countryCode, phoneNumber;
    private boolean processing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        setControl();

        getDataPreviousView();
        saveActivity();
        tvShowPhoneNumber.setText(countryCode + " " + phoneNumber);
        otpNumberMove();
    }

    public void saveActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    private void getDataPreviousView() {
        flag = getIntent().getStringExtra("flag");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        countryCode = getIntent().getStringExtra("countryCode");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        backendOTP = getIntent().getStringExtra("backendOTP");
    }

    public void onResendOTP(View v) {
        if (v.getId() == R.id.tvResendOTP) {
            PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(countryCode + phoneNumber)            // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onCodeSent(String otp, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            backendOTP = otp;
                            Toast.makeText(OTPVerification.this, "OTP sent successfully!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {
                            Toast.makeText(OTPVerification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
    }

    public void onVerify(View v) {
        if (v.getId() == R.id.btnVerify) {
            if (edtOTP1.getText().toString().trim().isEmpty()
             || edtOTP2.getText().toString().trim().isEmpty()
             || edtOTP3.getText().toString().trim().isEmpty()
             || edtOTP4.getText().toString().trim().isEmpty()
             || edtOTP5.getText().toString().trim().isEmpty()
             || edtOTP6.getText().toString().trim().isEmpty()) {
                Toast.makeText(OTPVerification.this, "Please enter all number!", Toast.LENGTH_LONG).show();
            } else {
                userInputOTP = edtOTP1.getText().toString() + edtOTP2.getText().toString()
                             + edtOTP3.getText().toString() + edtOTP4.getText().toString()
                             + edtOTP5.getText().toString() + edtOTP6.getText().toString();

                if(backendOTP != null) {
                    verifyCode(userInputOTP);
                } else {
                    Toast.makeText(OTPVerification.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void otpNumberMove() {
        edtOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtOTP1.getText().toString().trim().isEmpty()) {
                    edtOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtOTP2.getText().toString().trim().isEmpty()) {
                    edtOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtOTP3.getText().toString().trim().isEmpty()) {
                    edtOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtOTP4.getText().toString().trim().isEmpty()) {
                    edtOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtOTP5.getText().toString().trim().isEmpty()) {
                    edtOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(backendOTP, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (flag.equals("SignIn")) {
                            verifyOTPToSignIn(username, password);
                        } else if (flag.equals("SignUp")) {
                            verifyOTPToSignUp(username, password, phoneNumber);
                        } else if (flag.equals("ResetPW")) {
                            verifyOTPToResetPW(username, password);
                        }
                        if (processing) {
                            Intent i = new Intent(OTPVerification.this, MainRenovationActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(OTPVerification.this, "Please input correct OTP!", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void verifyOTPToSignIn(String username, String password) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.signIn(username, password);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse.getStatus() == 200) {
                    Toast.makeText(OTPVerification.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = true;
                }
                else {
                    Toast.makeText(OTPVerification.this,  resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = false;
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(OTPVerification.this, "Có lỗi đã xảy ra!", Toast.LENGTH_LONG).show();
                processing = false;
            }
        });
    }

    private void verifyOTPToSignUp(String username, String password, String phoneNumber) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);

        Call<ResultResponse> call = authenticationService.signUp(username, password, phoneNumber);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();

                if (resultResponse.getStatus() == 200) {
                    Toast.makeText(OTPVerification.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = true;
                } else {
                    Toast.makeText(OTPVerification.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = false;
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(OTPVerification.this, "Có lỗi đã xảy ra!", Toast.LENGTH_LONG).show();
                processing = false;
            }
        });
    }

    private void verifyOTPToResetPW(String username, String password) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);

        Call<ResultResponse> call = authenticationService.resetPassword(username, password, phoneNumber);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();

                if (resultResponse.getStatus() == 200) {
                    Toast.makeText(OTPVerification.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = true;
                } else {
                    Toast.makeText(OTPVerification.this, resultResponse.getMessage(), Toast.LENGTH_LONG).show();
                    processing = false;
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(OTPVerification.this, "Có lỗi đã xảy ra!", Toast.LENGTH_LONG).show();
                processing = false;
            }
        });
    }

    private void setControl() {
        edtOTP1 = findViewById(R.id.edtOTP1);
        edtOTP2 = findViewById(R.id.edtOTP2);
        edtOTP3 = findViewById(R.id.edtOTP3);
        edtOTP4 = findViewById(R.id.edtOTP4);
        edtOTP5 = findViewById(R.id.edtOTP5);
        edtOTP6 = findViewById(R.id.edtOTP6);

        tvShowPhoneNumber = findViewById(R.id.tvShowPhoneNumber);
        tvResendOTP = findViewById(R.id.tvResendOTP);

        btnVerify = findViewById(R.id.btnVerify);
    }
}