package com.example.ledflashlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private TextView onOffSwitch, sos;
    private Boolean isOn;
    private int milliseconds = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        onOffSwitch = findViewById(R.id.button_on_off);
        sos = findViewById(R.id.sos);
        isOn = false;
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            Log.v("FLASH APP", "no flash LED light");
            return;
        }
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        onOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isOn) {
                        onOffSwitch.setText("Turn ON");
                        turnOffFlashLight();
                        isOn = false;
                    } else {
                        onOffSwitch.setText("Turn OFF");
                        turnOnFlashLight();
                        isOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                shortRepeat(3);
                longRepeat(3);
                shortRepeat(3);
            }
        });
    }
    public void turnOnFlashLight() {
        try {
            cameraManager.setTorchMode(cameraId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }    }


    public void turnOffFlashLight() {

        try {
                cameraManager.setTorchMode(cameraId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void shortRepeat(int multiple){
        try {
            for(int i=0;i<multiple;i++){
            turnOnFlashLight();
            TimeUnit.MILLISECONDS.sleep(milliseconds);
            turnOffFlashLight();
            TimeUnit.MILLISECONDS.sleep(milliseconds);
            }

    }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public void longRepeat(int multiple){
        try {
            for(int i=0;i<multiple;i++){
                turnOnFlashLight();
                TimeUnit.SECONDS.sleep(1);
                turnOffFlashLight();
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            }

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
