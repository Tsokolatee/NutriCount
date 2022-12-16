package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Planner extends AppCompatActivity {
    private ImageView btnScan;
    private Button btnDashboard;
    private Button btnProgress;
    private Button btnProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(view -> {
            Intent intent = new Intent(Planner.this, Camera.class);
            startActivity(intent);
            finish();
        });

        btnDashboard = (Button) findViewById(R.id.btnDashboard);
        btnDashboard.setOnClickListener(view -> {
            Intent intent = new Intent(Planner.this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        btnProgress = (Button) findViewById(R.id.btnProgress);
        btnProgress.setOnClickListener(view -> {
            Intent intent = new Intent(Planner.this, Progress.class);
            startActivity(intent);
            finish();
        });

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Planner.this, Profile.class);
            startActivity(intent);
        });

    }
}