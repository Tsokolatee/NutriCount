package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Progress extends AppCompatActivity {
    private ImageView btnScan;
    private Button btnDashboard;
    private Button btnPlanner;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(view -> {
            Intent intent = new Intent(Progress.this, Camera.class);
            startActivity(intent);
            finish();
        });

        btnDashboard = (Button) findViewById(R.id.btnDashboard);
        btnDashboard.setOnClickListener(view -> {
            Intent intent = new Intent(Progress.this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        btnPlanner = (Button) findViewById(R.id.btnPlanner);
        btnPlanner.setOnClickListener(view -> {
            Intent intent = new Intent(Progress.this, Planner.class);
            startActivity(intent);
            finish();
        });

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Progress.this, Profile.class);
            startActivity(intent);
        });

    }
}