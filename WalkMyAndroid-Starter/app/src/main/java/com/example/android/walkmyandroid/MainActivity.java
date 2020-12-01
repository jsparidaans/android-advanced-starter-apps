/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.walkmyandroid;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TAG = "MainActivity";

    private Location lastLocation;
    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;
    private ImageView androidImageView;
    private AnimatorSet rotateAnim;
    private boolean isTrackingLocation;
    private Button locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get UI variables
        locationButton = findViewById(R.id.button_location);
        locationTextView = findViewById(R.id.textview_location);
        androidImageView = findViewById(R.id.imageview_android);
        rotateAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate);

        rotateAnim.setTarget(androidImageView);

        // Set listeners
        locationButton.setOnClickListener(v -> {
            if (!isTrackingLocation) {
                stopTrackingLocation();
            } else {
                startTrackingLocation();
            }
        });
    }

    @Override
    public void onTaskCompleted(String result) {
        locationTextView.setText(getString(R.string.address_text, result, System.currentTimeMillis()));
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    new FetchAddressTask(this, this).execute(location);
                } else {
                    locationTextView.setText(R.string.no_location);
                }
            });
        }
        rotateAnim.start();
        isTrackingLocation = true;
        locationTextView.setText(getString(R.string.address_text, getString(R.string.loading), System.currentTimeMillis()));
    }

    private void stopTrackingLocation() {
        if (isTrackingLocation) {

            isTrackingLocation = false;
            locationButton.setText(R.string.start_tracking);
            locationTextView.setText(R.string.textview_hint);
            rotateAnim.end();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
