package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class LogIn extends AppCompatActivity {
    private Button btnSignUp;
    private Button btnSignIn;

    private View.OnClickListener goSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LogIn.this, RegistrationP1.class));
        }
    };

    private View.OnClickListener goLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LogIn.this, Dashboard.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(goSignUp);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(goLogin);
    }
}