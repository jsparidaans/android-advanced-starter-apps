package com.dhl_myid.sensorsurvey;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;

    //Sensors
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    //TextViews
    private TextView mTextSensorProximity;
    private TextView mTextSensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve sensor manager from system service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Get views
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        //Get sensors
        mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //Set error string if no sensor available
        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }


    }
}