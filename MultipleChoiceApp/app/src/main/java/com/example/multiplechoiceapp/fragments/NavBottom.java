package com.example.multiplechoiceapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Hien.MainActivity;
import com.example.multiplechoiceapp.activities.TRIEU.MainRenovationActivity;

public class NavBottom extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.navbottom, container, false);

        // Get references to your ImageButtons
        ImageButton imageButton1 = view.findViewById(R.id.imageButton1);
        ImageButton imageButton2 = view.findViewById(R.id.imageButton2);
        ImageButton imageButton3 = view.findViewById(R.id.imageButton3);

        // Set onClickListeners for your ImageButtons if needed
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainRenovationActivity.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainRenovationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
