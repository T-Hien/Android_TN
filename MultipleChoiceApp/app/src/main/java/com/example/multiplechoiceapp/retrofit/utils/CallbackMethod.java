package com.example.multiplechoiceapp.retrofit.utils;

public interface CallbackMethod {
    void onSuccess(String information);

    void onSuccess(String a, String b);

    void onFailure(String errorMessage);
}
