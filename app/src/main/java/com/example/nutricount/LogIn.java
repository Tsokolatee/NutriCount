package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.content.Intent;

public class LogIn extends AppCompatActivity {
    private EditText inputUsername, inputPassword;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        db = new SQLiteHelper(this);
        db.populateInitialAccounts();

        // Navigation bind
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LogIn.this, RegistrationP1.class);
            startActivity(intent);
            finish();
        });

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(view -> {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();

            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                Boolean validUsername = db.checkUsername(username);

                if (validUsername) {
                    Boolean validCredentials = db.checkUsernamePassword(username, password);

                    if (validCredentials) {
                        Intent intent = new Intent(LogIn.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else if (!validCredentials) {
                        Toast.makeText(LogIn.this, "Password incorrect.", Toast.LENGTH_SHORT).show();
                    }
                } else if (!validUsername) {
                    Toast.makeText(LogIn.this, "Username/e-mail not recognized.", Toast.LENGTH_SHORT).show();
                }
            } else if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LogIn.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}