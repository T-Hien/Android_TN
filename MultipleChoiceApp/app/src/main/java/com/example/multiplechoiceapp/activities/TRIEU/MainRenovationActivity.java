package com.example.multiplechoiceapp.activities.TRIEU;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.fragments.HomeFragment;
import com.example.multiplechoiceapp.fragments.NotificationFragment;
import com.example.multiplechoiceapp.fragments.PersonFragment;
import com.example.multiplechoiceapp.fragments.StatisticalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainRenovationActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_renovation);
        setControl();
        loadFrameLayout(new HomeFragment(),true);
        navigateFrameLayout();
    }

    private void setControl(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);
    }

    private void navigateFrameLayout(){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.homeFrame){
                loadFrameLayout(new HomeFragment(),false);
            } else if(itemId == R.id.notificationFrame){
                loadFrameLayout(new NotificationFragment(),false);
            } else if(itemId == R.id.personFrame){
                loadFrameLayout(new PersonFragment(),false);
            } else  if(itemId == R.id.statisticalFrame){
                loadFrameLayout(new StatisticalFragment(),false);
            }
            return  true;
        });
    }

    private void loadFrameLayout(Fragment fragment, boolean isAppInitialized){
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isAppInitialized){
            transaction.add(R.id.frameLayout, fragment);
        } else {
            transaction.replace(R.id.frameLayout, fragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

}