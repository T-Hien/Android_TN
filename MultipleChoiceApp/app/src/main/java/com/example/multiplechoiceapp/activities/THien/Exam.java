



package com.example.multiplechoiceapp.activities.THien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.adapters.CustomAdapterSelection;
import com.example.multiplechoiceapp.models.Question;
import com.example.multiplechoiceapp.models.Selection;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;

import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Exam extends AppCompatActivity {
    private TextView txtContextExam, txtSentence, txtTime;
    private Button btnAfterExam, btnBeforeExam, btnSubmitExam;
    private ListView lvDanhsach;
    private Spinner snListQuestion;
    private int index = 0;
    private Float duration = 0.0F;
    private long time = 0;
    private int minutes;
    private int seconds;
    private List<String> myQuestion = new ArrayList<>();
    private List<String> myAnswer = new ArrayList<>();
    private List<String> myList = new ArrayList<>();
    private List<Long> myQuestionCode= new ArrayList<>();
    private List<Integer> questionIndex = new ArrayList<>();
    private List<Integer> ListIndex = new ArrayList<>();
    private CustomAdapterSelection customAdapterSelection;
    private ArrayAdapter<String> adapter;
    private Long topicSetCode = null;
    private Long assID = 0L;
    private String Username = "";
    private int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        setControl();
        setEvent();
        startTime();
    }
    private void startTime() {
        CountDownTimer countDowTime = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                time = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                time = 0;
                updateCountDownText();
                btnAfterExam.setEnabled(false);
                btnBeforeExam.setEnabled(false);

            }
        }.start();
    }
    private void updateCountDownText() {
        minutes=(int)((time/1000)/60);
        seconds = (int)((time/1000)%60);
        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        txtTime.setText(timeFormatted);
        if(time<10000){
            txtTime.setTextColor(Color.RED);
        }
        else{
            txtTime.setTextColor(Color.BLACK);
        }
    }
    private void setEvent() {
        btnBeforeExam.setEnabled(false);
        // Mã của topicSet
        Intent intent = getIntent();
        if (intent != null) {
            duration = intent.getFloatExtra("DURATION",0);
            Username = intent.getStringExtra("USERNAME");
            topicSetCode = intent.getLongExtra("ID_TOPICSET",0);
            level = intent.getIntExtra("LEVEL",0);
        }
        int timee = Math.round(duration);
        time = timee*60*1000;
        RetrofitClient retrofitClient = new RetrofitClient();
        getQuestion(retrofitClient,topicSetCode,level);
        btnAfterExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index +=1;
                btnBeforeExam.setEnabled(true);
                int position = Integer.valueOf(myList.get(index));
                customAdapterSelection.setSelectedItem(position-1);
                customAdapterSelection.notifyDataSetChanged();
                if(index+1 <myList.size()){
                    btnAfterExam.setEnabled(true);
                }
                else{
                    btnAfterExam.setEnabled(false);
                }
                int selectionPosition = adapter.getPosition("List Question");
                snListQuestion.setSelection(selectionPosition);
                //int t = questionIndex.get(index);
                int t = ListIndex.get(index);
                Long code = myQuestionCode.get(t);
                //Long code = myQuestionCode.get(t-1);
                getSelection(retrofitClient,code);

            }
        });
        btnBeforeExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index -=1;
                btnAfterExam.setEnabled(true);
                if(index>0){
                    btnBeforeExam.setEnabled(true);
                }
                else{
                    btnBeforeExam.setEnabled(false);
                }
                int selectionPosition = adapter.getPosition("List Question");
                snListQuestion.setSelection(selectionPosition);
//                int t = questionIndex.get(index);
//                Long code = myQuestionCode.get(t-1);
                int t = ListIndex.get(index);
                Long code = myQuestionCode.get(t);
                getSelection(retrofitClient,code);

            }
        });

        lvDanhsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapterSelection.setSelectedItem(position);
                myList.set(index,String.valueOf(position+1));
                String tam="";
                for(int j=0;j<myList.size();j++){
                    tam +=myList.get(j);
                }
            }
        });

        btnSubmitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Chuyen dap an - Chu thanh so
                String input = "";
                int k =0;
                for (int j = 0;j<myAnswer.size();j++) {
                    input +=myAnswer.get(j)+",";
                    k ++;
                }
                String[] numbers = input.split(",");
                StringBuilder result = new StringBuilder();
                for (String number : numbers) {
                    char letter = number.charAt(0);
                    int num = (int) (letter - 'A' + 1);
                    char convertedChar = (char) ('1' + num - 1);
                    result.append(convertedChar);
                }
                String dapan = result.toString();

                //All dap an câu hỏi dưới dạng số
                List<String> lessonAnswer = new ArrayList<>();
                String[] arrDapan = dapan.split("");
                String dapanCH = "";
                for (String character : arrDapan) {
                    lessonAnswer.add(character);
                    dapanCH +=character;

                }
                List<String> listDA = new ArrayList<>();
                for(int j=0;j<myList.size();j++){
                    int d = ListIndex.get(j);
                    listDA.add(lessonAnswer.get(d));
                }

                Float diem = 0F;
                int socau = 0;
                for (int j =0;j<myList.size();j++){
                    if(myList.get(j).equals(listDA.get(j))){
                        socau ++;
                    }
                }
                float phutdalam=0F;
                float giaydalam=0F;
                if(minutes>0){
                    if(seconds==0) {
                        giaydalam = 0;
                        phutdalam =(timee - minutes);
                    }
                    else{
                        phutdalam = (timee - minutes)-1;
                        giaydalam = 60-seconds;
                    }

                }
                else if(minutes==0){
                    if(seconds==0) {
                        giaydalam = 0;
                        phutdalam =timee;
                    }
                    else{
                        phutdalam = timee - seconds;
                        giaydalam = 60-seconds;
                    }
                }
                float tg = phutdalam + giaydalam/60;
                float thoigian = (float) (Math.round(tg * 100.0) / 100.0);
                if(socau==0){
                    diem = 0F;
                }
                else{
                    diem = (float) ((10.0/myList.size())*socau);
                }
                addAssignment(thoigian, topicSetCode, new CallbackMethod() {
                    @Override
                    public void onSuccess(String information) {
                    }
                    @Override
                    public void onSuccess(String a, String b) {
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("onFailure", "UnSubmit: : "+errorMessage);
                    }
                });
                Intent intent = new Intent(Exam.this, SaveExamResult.class);
                intent.putExtra("DIEM",diem);
                intent.putExtra("USERNAME",Username);
                intent.putExtra("SO_CAUTL",socau);
                intent.putExtra("SO_CAUH",myList.size());
                intent.putExtra("THOI_GIAN",thoigian);
                intent.putExtra("DURATION",duration);
                intent.putExtra("ID_TOPICSET",topicSetCode);
                v.getContext().startActivity(intent);

            }
        });

        snListQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(myList.size()>0){
                    int i = position-1;
                    btnAfterExam.setEnabled(true);
                    if(position !=0){
                        if(position == myList.size()){
                            btnAfterExam.setEnabled(false);
                        }
                        if(i==0){
                            btnBeforeExam.setEnabled(false);
                        }
                        else{
                            btnBeforeExam.setEnabled(true);
                        }
//                        int t = questionIndex.get(i);
//                        Long code = myQuestionCode.get(t-1);
//                        int t = ListIndex.get(index);
//                        Long code = myQuestionCode.get(t);
                        int t = ListIndex.get(index);
                        Long code = myQuestionCode.get(t);
                        getSelection(retrofitClient,code);
                        index = position -1;
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addAssignment(float duration, Long topicSetID, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.addAssignment(duration,topicSetID,Username);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (!response.isSuccessful()) {
                    callback.onFailure("Yêu cầu không thành công, mã trạng thái: " + response);
                    return;
                }
                if (resultResponse != null) {
                    Object assignmentID = resultResponse.getData();
                    assID = Long.parseLong(assignmentID.toString().split("\\.")[0]);
                    for(int j = 0;j<myList.size();j++){
                        Long t = Long.valueOf(questionIndex.get(j));
                        add_DetailAssignment(assID, t, myList.get(j),new CallbackMethod() {
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
                    if (resultResponse.getStatus() == 200) {
                        if (callback != null) {
                            callback.onSuccess(resultResponse.getMessage());
                        } else {
                        }
                    } else {
                        if (callback != null) {
                            callback.onFailure(resultResponse.getMessage());
                        } else {
                        }
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure("Phản hồi không hợp lệ!");
                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure("Có lỗi đã xảy ra! " + t.getMessage());
                } else {
                }
            }
        });
    }
    private void add_DetailAssignment(Long assignmentID, Long questionID, String Answer,CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.add_DetailAssignment(assignmentID,questionID,Answer);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (!response.isSuccessful()) {
                    callback.onFailure("Yêu cầu không thành công, mã trạng thái: " + response);
                    Log.e("ERR",resultResponse.getMessage());
                    return;
                }


                if (resultResponse != null) {
                    if (resultResponse.getStatus() == 200) {
                        if (callback != null) {
                            callback.onSuccess(resultResponse.getMessage());
                        } else {
                        }
                    } else {
                        if (callback != null) {
                            callback.onFailure(resultResponse.getMessage());
                        } else {
                        }
                    }
                } else {
                    if (callback != null) {
                        callback.onFailure("Phản hồi không hợp lệ!");
                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure("Có lỗi đã xảy ra! " + t.getMessage());
                } else {
                }
            }
        });
    }


    public void getQuestion(RetrofitClient retrofitClient, Long topicSetCode, int level){
        retrofitClient.getQuestionByLevel(topicSetCode,level, new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    List<Question> questions = response.body();
                    if (questions != null) {
                        List<String> selec = new ArrayList<>();
                        selec.add("List Question");
                        int j = 0;
                        String cau ="";
                        String tam ="";
                        for (Question question : questions) {
                            tam = String.valueOf(question.getquestionID());
                            txtContextExam.setText(tam);
                            myQuestion.add(question.getQuestionContent());
                            myAnswer.add(question.getAnswer());
                            myQuestionCode.add(question.getquestionID());
                            myList.add("0");
                            cau ="Câu "+(j+1);
                            selec.add(cau);
                            j ++;
                        }
                        //********************

                        Random random = new Random();

                        // Khởi tạo mảng chứa số ngẫu nhiên cho mỗi question
                        long[] randomNumbers = new long[myQuestionCode.size()];

                        // Đưa questionIndex và ListIndex vào mảng
                        for (int i = 0; i < myQuestionCode.size(); i++) {
                            randomNumbers[i] = myQuestionCode.get(i);
                        }
                        // Sắp xếp lại một lần nữa
                        for (int i = randomNumbers.length - 1; i > 0; i--) {
                            int index = random.nextInt(i + 1);
                            long temp = randomNumbers[index];
                            randomNumbers[index] = randomNumbers[i];
                            randomNumbers[i] = temp;
                        }

                        // Thêm questionIndex đã được random vào danh sách
                        for(long num : randomNumbers){
                            questionIndex.add((int)num);
                        }
                        // Sắp xếp theo randomNumbers
                        for (int i = 0; i < questionIndex.size(); i++) {
                            for (int k = 0; k < questionIndex.size(); k++) {
                                if (Long.valueOf(questionIndex.get(i))==myQuestionCode.get(k)) {
                                    ListIndex.add(k);
                                }
                            }
                        }
                        //*********************
                        adapter = new ArrayAdapter<>(Exam.this,android.R.layout.simple_spinner_item, selec);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        snListQuestion.setAdapter(adapter);
                        int selectionPosition = adapter.getPosition("List Question");
                        snListQuestion.setSelection(selectionPosition);
                        if(myQuestionCode.size()>0){
                            int t = ListIndex.get(index);
                            Long code = myQuestionCode.get(t);
                            getSelection(retrofitClient, code);
                        }
                    } else {
                        Log.e("Response", "List of questions is null");
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e("onFailure", "Error: "+t.getMessage());
            }
        });

    }

    private void getSelection(RetrofitClient retrofitClient,Long questionsCode) {
        retrofitClient.getSelection(questionsCode, new Callback<List<Selection>>(){
            @Override
            public void onResponse(Call<List<Selection>> call, Response<List<Selection>> response) {
                if (response.isSuccessful()) {
                    List<Selection> selections = response.body();
                    String t = "Câu "+(index+1)+"/"+myList.size();
                    txtSentence.setText(t);
                    txtContextExam.setText(myQuestion.get(ListIndex.get(index)));
                    txtContextExam.setText(myQuestion.get(ListIndex.get(index)));
                    if (selections != null) {
                        customAdapterSelection = new CustomAdapterSelection(Exam.this, R.layout.layout_list_question, selections);
                        lvDanhsach.setAdapter(customAdapterSelection);
                        if (!myList.get(index).equals("0")) {
                            int position = Integer.valueOf(myList.get(index));
                            customAdapterSelection.setSelectedItem(position-1);
                            customAdapterSelection.notifyDataSetChanged();
                        }
                    } else {
                        Log.e("Response", "List of Selections is null");
                    }
                } else {
                    Log.e("Response", "UnSucc: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Selection>> call, Throwable t) {
                Log.e("onFailure", "Erro:"+t.getMessage());
            }
        });
    }

    private void setControl(){
        txtSentence = findViewById(R.id.txtSentence);
        txtContextExam = findViewById(R.id.txtContextExam);
        btnBeforeExam = findViewById(R.id.btnBeforeExam);
        btnAfterExam = findViewById(R.id.btnAfterExam);
        btnSubmitExam = findViewById(R.id.btnSubmitExam);
        snListQuestion = findViewById(R.id.snlistExam);
        txtTime = findViewById(R.id.txtTime);
        lvDanhsach = findViewById(R.id.lvDanhsach);

    }
}

