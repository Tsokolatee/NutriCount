package com.example.nutricount;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class RegistrationP1 extends AppCompatActivity implements Serializable {
    private Account registerData;
    private EditText inputRegEmail;
    private EditText inputRegPassword;
    private EditText inputCPassword;
    private EditText inputFName;
    private EditText inputLName;
    private EditText inputBDay;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp1);

        // E-mail bind
        inputRegEmail = findViewById(R.id.inputRegEmail);
        inputRegEmail.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputRegEmail.getText().toString(), "email");

                if (val != null) {
                    registerData.setEmail(val);
                }
            }
        });
        // Password bind
        inputRegPassword = findViewById(R.id.inputRegPassword);
        inputRegPassword.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputFName.getText().toString(), null);

                if (val != null) {
                    registerData.setFirstName(val);
                }
            }
        });
        // Confirm Password bind
        inputCPassword = findViewById(R.id.inputCPassword);
        // First Name bind
        inputFName = findViewById(R.id.inputFName);
        inputFName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputFName.getText().toString(), null);

                if (val != null) {
                    registerData.setFirstName(val);
                }
            }
        });
        // Last Name bind
        inputLName = findViewById(R.id.inputLName);
        inputLName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputLName.getText().toString(), null);

                if (val != null) {
                    registerData.setLastName(val);
                }
            }
        });
        // Birthday bind
        inputBDay = findViewById(R.id.inputBDay);
        // Gender bind
        radioGroup = findViewById(R.id.radioGroup);

//        RadioButton genderSelected = findViewById(genderSelection.getCheckedRadioButtonId());
//        String gender = genderSelected.getText().toString();
//        gender = gender.substring(gender.lastIndexOf("rbtn") + 1);

        registerData = new Account(
                -1,
                null,
                null,
                null,
                null,
                null,
                -1,
                -1,
                -1,
                new ArrayList<>()
        );
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        registerData = (Account) getIntent().getSerializableExtra("account");
                    }
                }
        );

        TextView btnBack = (TextView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> goBack());

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(view -> {
            // Retain data when switching to part two of registration
            Intent intent = new Intent(getApplicationContext(), RegistrationP2.class);
            intent.putExtra("account", registerData);
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        Intent intent = new Intent(RegistrationP1.this, LogIn.class);

        startActivity(intent);
        finish();
    }

    private String formatStringData(String value, String method) {
        if (!TextUtils.isEmpty(value)) {
            switch (method) {
                case "email":
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    value = value.trim();
                    return (value.matches(emailPattern) && value.length() > 0) ? value : null;
                default:
                    value = value.trim();
                    value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                    return value;
            }
        } else {
            return null;
        }
    }

    private void getData() {

    }
}
