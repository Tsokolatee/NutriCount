package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.*;

public class RegistrationP2 extends AppCompatActivity {
    private Button btnRegister;
    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);
        // handle caught data

        btnBack = (TextView) findViewById(R.id.btnRegP2Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // migrate data to next activity
                Intent intent = new Intent(RegistrationP2.this, Verification.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }

    private void goBack() {
        // migrate data to next activity
        Intent intent = new Intent(RegistrationP2.this, RegistrationP1.class);
        startActivity(intent);
        finish();
    }
}