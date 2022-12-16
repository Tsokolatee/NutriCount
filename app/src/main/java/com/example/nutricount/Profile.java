package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private TextView btnBackProfile;
    private TextView profileName;
    private TextView profileAge;
    private TextView profileHeight;
    private TextView profileWeight;
    private TextView profileGoalWeight;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new SQLiteHelper(this);
        Account user = db.getAccount(db.LoggedInEmail);
        profileName = findViewById(R.id.profileName);
        profileName.setText(user.getFirstName() + " " + user.getLastName());
        profileAge = findViewById(R.id.profileAge);
        profileAge.setText(user.getBirthday());
        profileHeight = findViewById(R.id.profileHeight);
        profileHeight.setText(String.valueOf(user.getHeight()) + " cm");
        profileWeight = findViewById(R.id.profileWeight);
        profileWeight.setText(String.valueOf(user.getWeight()) + " kg");
        profileGoalWeight = findViewById(R.id.profileGoalWeight);
        profileGoalWeight.setText(String.valueOf(user.getGoal()) + " kg");

        btnBackProfile = (TextView) findViewById(R.id.btnBackProfile);
        btnBackProfile.setOnClickListener(view -> {
            finish();
        });

    }
}