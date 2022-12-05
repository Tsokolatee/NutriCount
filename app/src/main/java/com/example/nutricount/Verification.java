package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Verification extends AppCompatActivity {
    private TextView btnBack;
    private Button btnVerify;

    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Verification.this, RegistrationP2.class));
        }
    };

    private View.OnClickListener goVerify = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Verification.this, LogIn.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        btnBack = (TextView) findViewById(R.id.btnOTPBack);
        btnBack.setOnClickListener(goBack);

        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(goVerify);
    }
}