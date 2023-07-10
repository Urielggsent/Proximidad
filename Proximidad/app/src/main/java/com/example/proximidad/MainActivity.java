package com.example.proximidad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float proximityValue = sensorEvent.values[0];
                if (proximityValue < proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                } else {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // No se utiliza en el sensor de proximidad, pero debe ser implementado
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}
