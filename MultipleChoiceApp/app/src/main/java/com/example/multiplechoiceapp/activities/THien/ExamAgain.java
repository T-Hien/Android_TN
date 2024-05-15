

package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.multiplechoiceapp.adapters.CustomAdapterSelectionAgain;
import com.example.multiplechoiceapp.models.Assignment;
import com.example.multiplechoiceapp.models.Detailed_Assignment;
import com.example.multiplechoiceapp.models.Selection;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamAgain extends AppCompatActivity {
    private TextView txtContextExam, txtSentence;
    private Button btnAfterExam, btnBeforeExam, btnDetailExam;
    private ListView lvDanhsach;
    private Spinner snListQuestion;
    private int index = 0;
    private List<String> myQuestion = new ArrayList<>();
    private List<String> myAnswer = new ArrayList<>();
    private List<String> myList = new ArrayList<>();
    private List<Long> myQuestionCode= new ArrayList<>();
    private CustomAdapterSelectionAgain customAdapterSelection;
    private ArrayAdapter<String> adapter;
    private Long topicSetCode = null;
    private String Username="";
    private Long assignmentID = 0L;
    float duration = 0F;
    private float thoigian=0F;
    private String dapanCH = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examagain);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnBeforeExam.setEnabled(false);
        Intent intent = getIntent();
        if (intent != null) {
            Username = intent.getStringExtra("USERNAME");
            topicSetCode = intent.getLongExtra("ID_TOPICSET",0);
            duration = intent.getFloatExtra("DURATION",0);
        }
        RetrofitClient retrofitClient = new RetrofitClient();
        getAssignment(retrofitClient);
        btnAfterExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index +=1;
                btnBeforeExam.setEnabled(true);
                if(index+1 <myList.size()){
                    btnAfterExam.setEnabled(true);
                }
                else{
                    btnAfterExam.setEnabled(false);
                }
                int selectionPosition = adapter.getPosition("List Question");
                snListQuestion.setSelection(selectionPosition);
                Long code = myQuestionCode.get(index);
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
                Long code = myQuestionCode.get(index);
                getSelection(retrofitClient,code);

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
                        Long code = myQuestionCode.get(i);
                        getSelection(retrofitClient,code);
                        index = position -1;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDetailExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float diem = 0F;
                int socau = 0;
                for (int j =0;j<myList.size();j++){
                    if(myList.get(j).equals(myAnswer.get(j))){
                        socau ++;
                    }
                }
                diem = (float) ((10.0/myList.size())*socau);
                Intent intent = new Intent(ExamAgain.this, SaveExamResult.class);
                intent.putExtra("DIEM",diem);
                intent.putExtra("SO_CAUTL",socau);
                intent.putExtra("SO_CAUH",myList.size());
                intent.putExtra("USERNAME",Username);
                intent.putExtra("DAP_AN",dapanCH);
                intent.putExtra("THOI_GIAN",thoigian);
                intent.putExtra("DURATION",duration);
                intent.putExtra("ID_TOPICSET",topicSetCode);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void getAssignment(RetrofitClient retrofitClient){
        retrofitClient.getAssignment(topicSetCode,Username, new Callback<List<Assignment>>() {
            @Override
            public void onResponse(Call<List<Assignment>> call, Response<List<Assignment>> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        List<Assignment> assignments = response.body();
                        if (assignments != null && !assignments.isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Assignment assignment : assignments) {
                                assignmentID = assignment.getAssignmentCode();
                            }
                            getDetailedAssignments(retrofitClient);

                        } else {
                            Log.e("Response", "No assignments found");
                        }
                    } else {
                        Log.e("Response", "Unsuccessful: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Assignment>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    private void getDetailedAssignments(RetrofitClient retrofitClient) {
        retrofitClient.getDetailAssignment(assignmentID, new Callback<List<Detailed_Assignment>>() {
            @Override
            public void onResponse(Call<List<Detailed_Assignment>> call, Response<List<Detailed_Assignment>> response) {
                if (response.isSuccessful()) {
                    List<Detailed_Assignment> detailedAssignments = response.body();
                    if (detailedAssignments != null && !detailedAssignments.isEmpty()) {
                        List<String> selec = new ArrayList<>();
                        selec.add("List Question");
                        int v = 0;
                        String cau ="";
                        String tam ="";
                        String a ="",b="";
                        for (Detailed_Assignment detailedAssignment : detailedAssignments) {
                            myQuestionCode.add(detailedAssignment.getId().getQuestionID());
                            myList.add(detailedAssignment.getSelectedAnswer());
                            myAnswer.add(detailedAssignment.getQuestion().getAnswer());
                            myQuestion.add(detailedAssignment.getQuestion().getQuestionContent());
                            thoigian = detailedAssignment.getAssignment().getDuration();
                            cau ="Câu "+(v+1);
                            selec.add(cau);
                            v ++;
                        }
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
                            char letter = number.charAt(0); // Lấy ký tự từ chuỗi số
                            int num = (int) (letter - 'A' + 1); // Chuyển chữ cái thành số tương ứng ('A' là 1, 'B' là 2, v.v.)
                            char convertedChar = (char) ('1' + num - 1); // Chuyển số thành ký tự tương ứng ('1' tương ứng với số 1)
                            result.append(convertedChar);
                        }
                        String dapan = result.toString();

                        //All dap an câu hỏi dưới dạng số
                        List<String> lessonAnswer = new ArrayList<>();
                        myAnswer.clear();
                        String[] arrDapan = dapan.split("");

                        for (String character : arrDapan) {
                            lessonAnswer.add(character);
                            myAnswer.add(character);
                            dapanCH +=character;
                        }
                        adapter = new ArrayAdapter<>(ExamAgain.this,android.R.layout.simple_spinner_item, selec);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        snListQuestion.setAdapter(adapter);
                        int selectionPosition = adapter.getPosition("List Question");
                        snListQuestion.setSelection(selectionPosition);
                        if(myQuestionCode.size()>0){
                            Long code = myQuestionCode.get(index);
                            getSelection(retrofitClient, code);
                        }
                    }
                } else {
                    Log.e("Response getDetail", "Unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Detailed_Assignment>> call, Throwable t) {
                Log.e("onFailure getDetail", "Failed: " + t.getMessage());
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
                    txtContextExam.setText(myQuestion.get(index));
                    if (selections != null) {
                        customAdapterSelection = new CustomAdapterSelectionAgain(ExamAgain.this, R.layout.layout_list_question, selections);
                        lvDanhsach.setAdapter(customAdapterSelection);
                        int position = Integer.valueOf(myList.get(index));
                        int selectedAnswer = Integer.valueOf(myAnswer.get(index));
                        customAdapterSelection.setSelectedItem(position - 1, selectedAnswer - 1);
                        customAdapterSelection.notifyDataSetChanged();

                    } else {
                        Log.e("Response", "List of questions is null"+response.message());
                    }
                } else {
                    Log.e("Response", "getSelection: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Selection>> call, Throwable t) {
                Log.e("onFailure", "getSelection: "+t.getMessage());
            }
        });
    }

    private void setControl(){
        txtSentence = findViewById(R.id.txtSentence1);
        txtContextExam = findViewById(R.id.txtContextExam1);
        btnBeforeExam = findViewById(R.id.btnBeforeExam1);
        btnAfterExam = findViewById(R.id.btnAfterExam1);
        snListQuestion = findViewById(R.id.snlistExam1);
        lvDanhsach = findViewById(R.id.lvDanhsachAgain);
        btnDetailExam = findViewById(R.id.btnDetailExam1);

    }
}
