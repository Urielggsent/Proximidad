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
        //Define el fondo de pantalla predeterminado como blanco
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));

         //se obtiene una instancia
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //Este listener capturará los cambios en el sensor de proximidad
        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //e obtiene el valor de proximidad del evento utilizando
                float proximityValue = sensorEvent.values[0];
                //Luego, se verifica si el objeto está cerca  y se cambia el color de fondo a azul claro
                if (proximityValue < proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                }
                //o se restablece a blanco
                else {
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.white));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // No se utiliza en el sensor de proximidad, pero debe ser implementado
            }
        };
    }
//se registran y desregistran el proximitySensorListener para capturar los eventos del sensor de proximidad. Esto se hace utilizando registerListener() y unregisterListener() del SensorManager.
    @Override
    protected void onResume() {
        // se registra el listener con sensorManager.registerListener() y se establece un retraso normal (SensorManager.SENSOR_DELAY_NORMAL) para obtener actualizaciones del sensor.
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //se desregistra el listener con sensorManager.unregisterListener() para liberar recursos y detener la captura de eventos del sensor de proximidad cuando la actividad está pausada o en segundo plano.
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}
