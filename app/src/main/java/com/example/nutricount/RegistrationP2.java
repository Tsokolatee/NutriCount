package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistrationP2 extends AppCompatActivity implements Serializable {
    private Account registerData;
    private EditText inputHeight;
    private EditText inputWeight;
    private EditText inputGWeight;
    private List<CheckBox> checkboxes = new ArrayList<>();
    private EditText inputSpecify;
    private List<String> allergy = new ArrayList<>();
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerp2);

        db = new SQLiteHelper(this);
        db.populateInitialAccounts();

        // Height bind
        inputHeight = findViewById(R.id.inputHeight);
        inputHeight.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputHeight.getText().toString(), "name");
                double result = validateDouble(val);

                if (result > 0) {
                    registerData.setHeight(result);
                }
            }
        });
        // Weight bind
        inputWeight = findViewById(R.id.inputWeight);
        inputWeight.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputWeight.getText().toString(), "");
                double result = validateDouble(val);

                if (result > 0) {
                    registerData.setWeight(result);
                }
            }
        });
        // Goal Weight bind
        inputGWeight = findViewById(R.id.inputGWeight);
        inputGWeight.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = formatStringData(inputGWeight.getText().toString(), "");
                double result = validateDouble(val);

                if (result > 0) {
                    registerData.setGoal(result);
                }
            }
        });
        // Allergy bind
        checkboxes.add(findViewById(R.id.cboxMilk));
        checkboxes.add(findViewById(R.id.cboxNuts));
        checkboxes.add(findViewById(R.id.cboxPeanuts));
        checkboxes.add(findViewById(R.id.cboxSoy));
        checkboxes.add(findViewById(R.id.cboxShrimp));
        checkboxes.add(findViewById(R.id.cboxGluten));
        checkboxes.add(findViewById(R.id.cboxCelery));
        checkboxes.add(findViewById(R.id.cboxSesameSeed));
        checkboxes.add(findViewById(R.id.cboxEggs));
        checkboxes.add(findViewById(R.id.cboxFish));
        for (CheckBox component: checkboxes) {
            component.setOnCheckedChangeListener((compoundButton, b) -> {
                String value = compoundButton.getText().toString();
                if (b) {
                    addAllergy(value);
                } else {
                    removeAllergy(value);
                }
            });
        }
        inputSpecify = findViewById(R.id.inputSpecify);
        inputSpecify.setOnFocusChangeListener((view, b) -> {
            if (!b) {
                String val = inputSpecify.getText().toString().trim();
                String[] allergyArray = val.split(",");

                for (String item: allergyArray) {
                    addAllergy(item);
                }
            }
        });

        // Receive data from part one of registration
        registerData = (Account) getIntent().getSerializableExtra("account");

        TextView btnBack = (TextView) findViewById(R.id.btnRegP2Back);
        btnBack.setOnClickListener(view -> goBack());

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            fillData();

            if (isPartTwoFilled()) {
                SQLiteDatabase dbReader = db.getWritableDatabase();

                // Add New Account
                db.createAccount(
                        dbReader,
                        registerData.getEmail(),
                        registerData.getPassword(),
                        registerData.getFirstName(),
                        registerData.getLastName(),
                        registerData.getGender(),
                        registerData.getBirthday(),
                        registerData.getHeight(),
                        registerData.getWeight(),
                        registerData.getGoal(),
                        registerData.getAllergy()
                );

                Intent intent = new Intent(RegistrationP2.this, Profile.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegistrationP2.this, "Please fill out all fields in this part before proceeding.", Toast.LENGTH_SHORT).show();
            }
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

    private String formatStringData(String value, String method) {
        if (!TextUtils.isEmpty(value)) {
            if ("name".equals(method)) {
                value = value.trim();
                value = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                return value;
            }

            value = value.trim();
            return value;
        } else {
            return "";
        }
    }

    private void addAllergy(String value) {
        if (!allergy.contains(value)) {
            allergy.add(value.trim());
        }
    }

    private void removeAllergy(String value) {
        if (allergy.contains(value)) {
            allergy.remove(value.trim());
        }
    }

    private double validateDouble(String value) {
        double returnValue;

        try {
            returnValue = Double.parseDouble(value);
            return returnValue;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void fillData() {
        double result = validateDouble(formatStringData(inputHeight.getText().toString(), "name"));
        if (result > 0) {
            registerData.setHeight(result);
        }
        result = validateDouble(formatStringData(inputWeight.getText().toString(), ""));
        if (result > 0) {
            registerData.setWeight(result);
        }
        result = validateDouble(formatStringData(inputGWeight.getText().toString(), ""));
        if (result > 0) {
            registerData.setGoal(result);
        }
        for (CheckBox component: checkboxes) {
            String value = component.getText().toString();
            if (component.isChecked()) {
                addAllergy(value);
            } else {
                removeAllergy(value);
            }
        }
        String val = inputSpecify.getText().toString().trim();
        String[] allergyArray = val.split(",");
        for (String item: allergyArray) {
            addAllergy(item);
        }
    }

    private boolean isPartTwoFilled() {
        String text =  String.valueOf(registerData.getHeight()) + " " + String.valueOf(registerData.getWeight()) + " " + String.valueOf(registerData.getGoal());

        return registerData.getHeight() > 0
                && registerData.getWeight() > 0
                && registerData.getGoal() > 0;
    }
}