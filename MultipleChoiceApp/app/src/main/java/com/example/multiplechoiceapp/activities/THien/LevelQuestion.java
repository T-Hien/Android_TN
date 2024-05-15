package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.example.multiplechoiceapp.models.Question;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LevelQuestion extends AppCompatActivity {

    private Button btnExamLevel, btnHomeLevelExam;
    private Spinner snLevel;
    private ArrayAdapter<String> adapter;
    private Float duration;
    private Long topicSetCode= 0L;
    private List<Integer> listLevel = new ArrayList<>();

    private String username = "";
    private int level = 1;
    private Boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_quesion);
        setControl();
        Intent intent = getIntent();
        if (intent != null) {
            duration = intent.getFloatExtra("DURATION",0);
            username = intent.getStringExtra("USERNAME");
            topicSetCode = intent.getLongExtra("ID_TOPICSET",0);
            setEvent();
        }
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        getLevel(topicSetCode,retrofitClient);
        List<String> selec = new ArrayList<>();
        selec.add("Dễ");
        selec.add("Vừa");
        selec.add("Khó");
        selec.add("Nâng cao");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, selec);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        snLevel.setAdapter(adapter);

        btnExamLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check){
                    Intent intent = new Intent(LevelQuestion.this, Exam.class);
                    intent.putExtra("DURATION",duration);
                    intent.putExtra("USERNAME",username);
                    intent.putExtra("ID_TOPICSET",topicSetCode);
                    intent.putExtra("LEVEL",level);
                    v.getContext().startActivity(intent);
                }
                else{
                    String Slevel ="";
                    switch (level) {
                        case 1:
                            Slevel = "Dễ";
                            break;
                        case 2:
                            Slevel = "Vừa";
                            break;
                        case 3:
                            Slevel = "Khó";
                            break;
                        default:
                            Slevel = "Nâng cao";
                            break;
                    }
                    Toast.makeText(LevelQuestion.this, "Không có bài thi thuộc level: "+Slevel, Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnHomeLevelExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LevelQuestion.this, MainRenovationActivity.class);
                v.getContext().startActivity(intent1);
            }
        });
        snLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    level = position +1;
                    for(int j =0;j<listLevel.size();j++){
                        if(level==listLevel.get(j)){
                            check = true;
                            break;
                        }
                        else{
                            check = false;
                        }
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
    private void getLevel(Long topicSetCode, RetrofitClient retrofitClient){
        retrofitClient.getListLevel(topicSetCode, new Callback<List<Integer>>() {

            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
                    List<Integer> list = response.body();
                    if (list != null) {
                        for(int i = 0;i<list.size();i++){
                            listLevel.add(list.get(i));

                        }
                        for(int j =0;j<listLevel.size();j++){
                            if(level==listLevel.get(j)){
                                check = true;
                                break;
                            }
                            else{
                                check = false;
                            }
                        }
                    }
                    else
                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        });
    }

    //@SuppressLint("WrongViewCast")
    private void setControl() {
        snLevel = findViewById(R.id.snListLevel);
        btnExamLevel = findViewById(R.id.btnExamLevel);
        btnHomeLevelExam = findViewById(R.id.btnHomeLevelExam);


    }
}