package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.*;

public class RegistrationP2 extends AppCompatActivity {
    private Button btnRegister;
    private TextView btnRegP2Back;

    private View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(RegistrationP2.this, RegistrationP1.class));
        }
    };

    private View.OnClickListener goRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(RegistrationP2.this, Verification.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);

        btnRegP2Back = (TextView) findViewById(R.id.btnRegP2Back);
        btnRegP2Back.setOnClickListener(goBack);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(goRegister);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}