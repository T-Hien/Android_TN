package com.example.multiplechoiceapp.retrofit;

import com.example.multiplechoiceapp.models.Assignment;
import com.example.multiplechoiceapp.models.Detailed_Assignment;
import com.example.multiplechoiceapp.models.Question;
import com.example.multiplechoiceapp.models.Selection;
import com.example.multiplechoiceapp.models.Topic;
import com.example.multiplechoiceapp.models.Topic_Set;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static String baseURL = "http://10.251.2.95:8080/api/v1/";

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void getQuestion(Long topicSetCode, Callback<List<Question>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Question>> call = authenticationService.getQuestion(topicSetCode);
        call.enqueue(callback);
    }
    public void getQuestionByLevel(Long topicSetCode,int level, Callback<List<Question>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Question>> call = authenticationService.getQuestionByLevel(topicSetCode,level);
        call.enqueue(callback);
    }
    public void getListLevel(Long topicSetCode, Callback<List<Integer>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Integer>> call = authenticationService.getListLevel(topicSetCode);
        call.enqueue(callback);
    }
    public void getSelection(Long questionCode, Callback<List<Selection>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Selection>> call2 = authenticationService.getSelection(questionCode);
        call2.enqueue(callback);
    }
    //    public void getTopicSet(Callback<List<Topic_Set>> callback) {
//        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
//        Call<List<Topic_Set>> call2 = authenticationService.getTopicSet();
//        call2.enqueue(callback);
//    }
    public void getAssignment(Long topicSetID, String username, Callback<List<Assignment>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Assignment>> call2 = authenticationService.getAssignment(topicSetID,username);
        call2.enqueue(callback);
    }
    public void getDetailAssignment(Long assignmentID, Callback<List<Detailed_Assignment>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Detailed_Assignment>> call2 = authenticationService.getListDetailedAssignment(assignmentID);
        call2.enqueue(callback);
    }
    public void getTopicHome(String username, Callback<List<Topic>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Topic>> call2 = authenticationService.getListTopicHome(username);
        call2.enqueue(callback);
    }
    public void getTopicSetByTopicID(Long topicID, Callback<List<Topic_Set>> callback) {
        AuthenticationService authenticationService = getClient().create(AuthenticationService.class);
        Call<List<Topic_Set>> call2 = authenticationService.getTopicSetByTopicID(topicID);
        call2.enqueue(callback);
    }
}
