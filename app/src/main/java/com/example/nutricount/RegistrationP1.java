package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class RegistrationP1 extends AppCompatActivity {
    private TextView btnBack;
    private Button btnNext;

    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(RegistrationP1.this, LogIn.class));
        }
    };

    private View.OnClickListener goNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(RegistrationP1.this, RegistrationP2.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp1);

        btnBack = (TextView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(goBack);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(goNext);
    }
}
