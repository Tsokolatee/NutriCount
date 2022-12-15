package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.content.Intent;

public class LogIn extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new SQLiteHelper(this);
        db.populateInitialAccounts();

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // Navigation bind
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LogIn.this, RegistrationP1.class);
            startActivity(intent);
            finish();
        });

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(view -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                Boolean validEmail = db.checkEmail(email);

                if (validEmail) {
                    Boolean validCredentials = db.checkCredentials(email, password);

                    if (validCredentials) {
                        Intent intent = new Intent(LogIn.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogIn.this, "Password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogIn.this, "E-mail not recognized.", Toast.LENGTH_SHORT).show();
                }
            } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LogIn.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}