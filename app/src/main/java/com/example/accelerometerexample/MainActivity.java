package com.example.accelerometerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor;
    private SensorManager manager;
    private float lastX;
    private float lastY;

    TextView xLabel;
    TextView yLabel;
    TextView zLabel;
    ImageView hamster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xLabel = findViewById(R.id.x);
        yLabel = findViewById(R.id.y);
        zLabel = findViewById(R.id.z);
        hamster = findViewById(R.id.hamster);

        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastX = 0;
        lastY = 0;
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(sensor != null){
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(sensor != null){
            manager.unregisterListener(this, sensor);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        float deltaX = lastX - event.values[0];
        float deltaY = lastY - event.values[1];

        xLabel.setText(Float.toString(event.values[0]));
        yLabel.setText(Float.toString(event.values[1]));
        zLabel.setText(Float.toString(event.values[2]));
        lastX = event.values[0];
        lastY = event.values[1];
        ObjectAnimator.ofFloat(hamster, "translationX", deltaX*2000).start();
        ObjectAnimator.ofFloat(hamster, "translationY", deltaY*2000).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
