package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SaveExamResult extends AppCompatActivity {

    private TextView txtTimeResult, txtExamResult, txtSelectResult;
    private Button btnRetestSaveExamResult, btnDetailSaveExamResult,btnHomeSaveExamResult;
    private String luachon;
    private int socautl,socauh;
    private float diem = 0F;
    private float thoigian = 0F;
    private Long topicSetCode =0L;
    private PieChart pieChart;
    private Float duration;

    private String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveexamresult);
        setControl();
        Intent intent = getIntent();
        if (intent != null) {
            //so cau da lam
            diem = intent.getFloatExtra("DIEM", 0);
            socautl = intent.getIntExtra("SO_CAUTL", 0);
            socauh = intent.getIntExtra("SO_CAUH", 0);
            topicSetCode = intent.getLongExtra("ID_TOPICSET",0);
            username = intent.getStringExtra("USERNAME");
            duration = intent.getFloatExtra("DURATION",0);
            thoigian = intent.getFloatExtra("THOI_GIAN",0);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String diemfm = decimalFormat.format(diem);
            String socau = "Số câu đúng:"+socautl+"/"+socauh;
            String d = "Điểm:  "+ diemfm;

            double minutes = thoigian;
            int totalSeconds = (int) (minutes * 60);
            int giaydalam = totalSeconds % 60;
            int phutdalam = totalSeconds / 60;

            String time = phutdalam + " phút "+giaydalam + " giây";
            time = "Thời gian đã làm: " + time;

            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            pieEntries.add(new PieEntry(socautl));
            pieEntries.add(new PieEntry(socauh-socautl));
            ArrayList<String> trangThai = new ArrayList<>();
            trangThai.add("Câu đúng");
            trangThai.add("Câu sai");
            ArrayList<Integer> datacolor = new ArrayList<Integer>();
            datacolor.add(Color.BLUE);
            datacolor.add(Color.RED);
            PieDataSet pieDataSet = new PieDataSet(pieEntries,"Đúng");
            pieDataSet.setColors(datacolor);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
            txtExamResult.setText(d);
            txtTimeResult.setText(time);
            txtSelectResult.setText(socau);

            setEvent();
        }
    }

    private void setEvent() {
        btnHomeSaveExamResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SaveExamResult.this, MainRenovationActivity.class);
                v.getContext().startActivity(intent1);
            }
        });
        btnDetailSaveExamResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SaveExamResult.this, ExamAgain.class);
                intent1.putExtra("ID_TOPICSET",topicSetCode);
                intent1.putExtra("USERNAME",username);
                intent1.putExtra("DURATION",duration);
                v.getContext().startActivity(intent1);
            }
        });
        btnRetestSaveExamResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //duration = topicSet.getDuration();
                Intent intent = new Intent(SaveExamResult.this, LevelQuestion.class);
                intent.putExtra("ID_TOPICSET",topicSetCode);
                intent.putExtra("DURATION",duration);
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }
        });
    }

    //@SuppressLint("WrongViewCast")
    private void setControl() {
        txtExamResult = findViewById(R.id.txtExamResult);
        txtTimeResult = findViewById(R.id.txtTimeResult);
        txtSelectResult = findViewById(R.id.txtSelectResult);
        btnDetailSaveExamResult = findViewById(R.id.btnDetailSaveExamResult);
        btnRetestSaveExamResult = findViewById(R.id.btnRetestSaveExamResult);
        pieChart = findViewById(R.id.pieBD);
        btnHomeSaveExamResult = findViewById(R.id.btnHomeSaveExamResult);

    }
}