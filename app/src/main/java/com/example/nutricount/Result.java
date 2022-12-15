package com.example.nutricount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.widget.*;

import com.example.nutricount.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result extends AppCompatActivity {
    private ImageView imgvwCaptured;
    private Bitmap bmp;
    private TextView result, confidence, txtvwConfidences;
    private TextView btnBackResult;

    private int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnBackResult = (TextView) findViewById(R.id.btnBackResult);
        btnBackResult.setOnClickListener(view -> {
            Intent intent = new Intent(Result.this, Camera.class);
            startActivity(intent);
            finish();
        });

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
        result = (TextView) findViewById(R.id.result);
        confidence = (TextView) findViewById(R.id.resultPreview);
        txtvwConfidences = (TextView) findViewById(R.id.txtvwConfidences);

        imgvwCaptured.setImageBitmap(preProcessImg());
        imgScan();
    }

    private Bitmap preProcessImg() {
        int imageHeight = bmp.getHeight();
        int imageWidth = bmp.getWidth();
        int minLength = Math.min(imageHeight, imageWidth);

        bmp = ThumbnailUtils.extractThumbnail(bmp, minLength, minLength);
        bmp = Bitmap.createScaledBitmap(bmp, imageSize, imageSize, false);

        return bmp;
    }

    private void imgScan(){
        try {
            Model mdl = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[] {1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            bmp.getPixels(intValues, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for (int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val >> 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = mdl.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            String s = "";
            String[] classes = {"Pasta", "Cereal", "Bread", "Milk", "Chicken", "Pork", "Cheese", "Sausage", "Egg", "Carrot", "Kiwi", "Papaya", "Lemon", "Cabbage", "Banana", "Orange", "Pear", "Apple", "Ampalaya", "Kalabasa", "Cucumber", "Corn", "Eggplant",};

            List<Confidence> list = new ArrayList<Confidence>(confidences.length);
            for(int i = 0; i < confidences.length; i++){
                list.add(new Confidence(i, confidences[i]));
            }
            Collections.sort(list, Collections.reverseOrder());

            result.setText(classes[list.get(0).index]);
            confidence.setText(String.format("Confidence LVL: %s", list.get(0).toString()));
            int index = 0;
            for(int i = 1; i <= 4; i++) {
                index = list.get(i).index;
                s += String.format("%d: %s (%s)\n", i + 1, classes[index], list.get(i).toString());
            }
            txtvwConfidences.setText(s);

            // Releases model resources if no longer used.
            mdl.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
}