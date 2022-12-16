package com.example.nutricount;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

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
        inputRegPassword.setOnFocusChangeListener((view, b) -> { evaluatePassword(b); });
        // Confirm Password bind
        inputCPassword = findViewById(R.id.inputCPassword);
        inputCPassword.setOnFocusChangeListener((view, b) -> { evaluatePassword(b); });
        // First Name bind
        inputFName = findViewById(R.id.inputFName);
        inputFName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputFName.getText().toString(), "name");

                if (val != null) {
                    registerData.setFirstName(val);
                }
            }
        });
        // Last Name bind
        inputLName = findViewById(R.id.inputLName);
        inputLName.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputLName.getText().toString(), "name");

                if (val != null) {
                    registerData.setLastName(val);
                }
            }
        });
        // Birthday bind
        inputBDay = findViewById(R.id.inputBDay);
        inputBDay.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputBDay.getText().toString(), "");

                if (val != null) {
                    registerData.setBirthday(val);
                }
            }
        });
        // Gender bind
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = findViewById(i);
            String gender = radioButton.getText().toString();
            registerData.setGender(gender.substring(gender.lastIndexOf("rbtn") + 1));
        });

        registerData = new Account(
                -1,
                null,
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
            fillData();

            if (isPartOneFilled()) {
                // Retain data when switching to part two of registration
                Intent intent = new Intent(getApplicationContext(), RegistrationP2.class);
                intent.putExtra("account", registerData);
                activityResultLauncher.launch(intent);
            } else {
                Toast.makeText(RegistrationP1.this, "Please fill out all fields in this part before proceeding.", Toast.LENGTH_SHORT).show();
            }
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
                    if (value.matches(emailPattern) && value.length() > 0) {
                        return value;
                    } else {
                        Toast.makeText(RegistrationP1.this, "Incorrect e-mail format.", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                case "name":
                    value = value.trim();
                    value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                    return value;
                default:
                    value = value.trim();
                    return value;
            }
        } else {
            return "";
        }
    }

    private void evaluatePassword(boolean b) {
        if (!b) {
            if (!TextUtils.isEmpty(inputRegPassword.getText().toString()) && !TextUtils.isEmpty(inputCPassword.getText().toString())) {
                String val = formatStringData(inputRegPassword.getText().toString(), "");
                String val2 = formatStringData(inputCPassword.getText().toString(), "");

                if (!TextUtils.isEmpty(val) && !TextUtils.isEmpty(val2) && val.equals(val2)) {
                    registerData.setPassword(val);
                } else {
                    Toast.makeText(RegistrationP1.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void fillData() {
        registerData.setEmail(formatStringData(inputRegEmail.getText().toString(), "email"));
        evaluatePassword(false);
        registerData.setFirstName(formatStringData(inputFName.getText().toString(), "name"));
        registerData.setLastName(formatStringData(inputLName.getText().toString(), "name"));
        registerData.setBirthday(formatStringData(inputBDay.getText().toString(), ""));
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String gender = radioButton.getText().toString();
        registerData.setGender(gender.substring(gender.lastIndexOf("rbtn") + 1));
    }

    private boolean isPartOneFilled() {
        return !TextUtils.isEmpty(registerData.getEmail())
                && !TextUtils.isEmpty(registerData.getPassword())
                && !TextUtils.isEmpty(registerData.getFirstName())
                && !TextUtils.isEmpty(registerData.getLastName())
                && !TextUtils.isEmpty(registerData.getGender())
                && !TextUtils.isEmpty(registerData.getBirthday());
    }
}
