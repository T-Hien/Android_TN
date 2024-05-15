package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;

public class Contruct extends AppCompatActivity {
    private Button btnExitContruct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_conduct);
        setEvent();
    }

    private void setEvent() {
        btnExitContruct = findViewById(R.id.btnExitContruct);
        btnExitContruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contruct.this, MainRenovationActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

}