package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class Profile extends AppCompatActivity {
    private TextView btnBackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBackProfile = (TextView) findViewById(R.id.btnBackProfile);
        btnBackProfile.setOnClickListener(view -> {
            finish();
        });

    }
}