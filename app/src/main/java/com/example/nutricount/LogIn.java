package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.Intent;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignUp;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:
                startActivity(new Intent(LogIn.this, RegistrationP1.class));
                break;

            case R.id.btnSignIn:
                startActivity(new Intent(LogIn.this, Dashboard.class));
                break;

            default:
                break;
        }

    }
}

// camelCase
// PascalCase
// snake_case