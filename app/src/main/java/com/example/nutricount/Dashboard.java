package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;

public class Dashboard extends AppCompatActivity {
    private ImageView btnScan;

    View.OnClickListener goScan = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Dashboard.this, Camera.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(goScan);
    }
}