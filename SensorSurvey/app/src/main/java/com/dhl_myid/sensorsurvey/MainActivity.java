package com.dhl_myid.sensorsurvey;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    //Sensors
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;

    //TextViews
    private TextView mTextSensorProximity;
    private TextView mTextSensorLight;
    private TextView mTextSensorHumidity;

    @Override
    protected void onStart() {
        super.onStart();

        //Register listeners
        if (mSensorProximity != null) {
            sensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            sensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumidity != null) {
            sensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Unregister listeners
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Detect changes and apply them
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity, currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(getResources().getString(R.string.label_humidity, currentValue));
            default:
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve sensor manager from system service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Get views
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

        //Get sensors
        mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        //Set error string if no sensor available
        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorHumidity == null) {
            mTextSensorHumidity.setText(sensor_error);
        }


    }
}