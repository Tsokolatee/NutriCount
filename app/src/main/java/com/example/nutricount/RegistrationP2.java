package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import java.io.Serializable;

public class RegistrationP2 extends AppCompatActivity implements Serializable {
    private Account registerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);

        // Receive data from part one of registration
        registerData = (Account) getIntent().getSerializableExtra("account");

        TextView btnBack = (TextView) findViewById(R.id.btnRegP2Back);
        btnBack.setOnClickListener(view -> goBack());

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            // migrate data to next activity
            Intent intent = new Intent(RegistrationP2.this, Verification.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        // Return updated data
        Intent intent = new Intent(RegistrationP2.this, RegistrationP1.class);
        intent.putExtra("account", registerData);
        setResult(RESULT_OK, getIntent());
        finish();
    }
}