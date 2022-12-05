package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.*;

import java.io.FileInputStream;

public class Result extends AppCompatActivity {
    private ImageView imgvwCaptured;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle extras = getIntent().getExtras();
        String capturedImgFilename = extras.getString("imgFilename");
        try {
            FileInputStream is = this.openFileInput(capturedImgFilename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgvwCaptured = (ImageView) findViewById(R.id.imgvwCaptured);
        imgvwCaptured.setImageBitmap(bmp);
    }
}