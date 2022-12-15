package com.example.nutricount;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.widget.*;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://www.youtube.com/watch?v=MhsG3jYEsek
// https://www.freecodecamp.org/news/android-camera2-api-take-photos-and-videos/
public class Camera extends AppCompatActivity {
    // Global Component Variables
    private TextureView textureView;

    // Orientations
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    // Camera Variables
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private CameraManager manager;
    private String cameraID;
    private boolean isTorchOn;

    // Handler Variables
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;

    // Captured Image Variables
    private Size imageDimension;
    private String capturedImgFilename;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Close Button bind
        ImageView btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(view -> goBack());

        // Camera View bind
        textureView = findViewById(R.id.txvwCameraVision);
        if (textureView != null)
            textureView.setSurfaceTextureListener(textureListener);

        // Camera Shutter bind
        ImageView btnShutter = findViewById(R.id.btnShutter);
        if (btnShutter != null)
            btnShutter.setOnClickListener(view -> takePicture());

        ImageView btnFlash = findViewById(R.id.btnFlash);
        btnFlash.setOnClickListener(view -> toggleTorch());
    }

    // Class Attributes
    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
            // Transform captured image size according to surface width and height
        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) { }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            // Called when camera is opened
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int i) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    // Class Functions
    private void openCamera() {
        try {
            // Choose which camera, then get its dimension
            manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraID = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraID);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;

            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

            // Add permission for camera and let user grant the permission if not yet granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Camera.this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }

            manager.openCamera(cameraID, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void createCameraPreview() {
        try {
            // Create surface, then add update handler
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);

            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    // The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }

                    // When session is ready, we start displaying the preview
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(Camera.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void takePicture() {
        if (cameraDevice == null) {
            Log.e(TAG, "cameraDevice is null");
            return;
        }

        try {
            Size[] imgSize = null;
            int width = 0;
            int height = 0;

            manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());

            if (characteristics != null) {
                imgSize = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }

            if (imgSize != null && imgSize.length > 0) {
                width = imgSize[0].getWidth();
                height = imgSize[0].getHeight();
            }

            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);

            List<Surface> outputSurfaces = new ArrayList<>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            // Orientation
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            ImageReader.OnImageAvailableListener readerListener = readerFunction -> {
                capturedImgFilename = "bitmap.png";

                try (Image img = readerFunction.acquireLatestImage()) {
                    ByteBuffer buffer = img.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes);

                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                    FileOutputStream stream = openFileOutput(capturedImgFilename, Context.MODE_PRIVATE);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            reader.setOnImageAvailableListener(readerListener, backgroundHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Intent intent = new Intent(Camera.this, Result.class);
                    intent.putExtra("imgFilename", capturedImgFilename);
                    startActivity(intent);
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, backgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) { }
            }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }

        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void toggleTorch() {
        isTorchOn = !isTorchOn;

        try {
            manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            cameraID = manager.getCameraIdList()[0];

            manager.setTorchMode(cameraID, isTorchOn);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void finish() {
        super.finish();
        try {
            manager.setTorchMode(cameraID, false);
        } catch (CameraAccessException e) {
            // prints stack trace on standard error
            // output error stream
            e.printStackTrace();
        }
    }

    // TODO OVERRIDE ON REQUEST PERMISSION RESULT (FOR CAMERA)

    // Handler and Threads Functions
    protected void startBackgroundThread() {
        backgroundThread = new HandlerThread("Camera Background");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    protected void stopBackgroundThread() {
        backgroundThread.quitSafely();

        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(TAG, "onResume");
        startBackgroundThread();

        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        //closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    // Navigation Functions
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        goBack();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void goBack() {
        startActivity(new Intent(Camera.this, Dashboard.class));
        finish();
    }
}