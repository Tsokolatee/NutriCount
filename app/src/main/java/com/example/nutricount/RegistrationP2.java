package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.*;

public class RegistrationP2 extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegister;
    private TextView btnRegP2Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnRegP2Back = (TextView) findViewById(R.id.btnRegP2Back);
        btnRegP2Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRegister:
                startActivity(new Intent(RegistrationP2.this, Verification.class));
                break;

            case R.id.btnRegP2Back:
                startActivity(new Intent(RegistrationP2.this, RegistrationP1.class));
                break;

            default:
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}