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
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TAG = "MainActivity";

    private Location lastLocation;
    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get UI variables
        Button locationButton = findViewById(R.id.button_location);
        locationTextView = findViewById(R.id.textview_location);

        // Set listeners
        locationButton.setOnClickListener(v -> getLocation());
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    lastLocation = location;
                    locationTextView.setText(
                            getString(R.string.location_text,
                                    lastLocation.getLatitude(),
                                    lastLocation.getLongitude(),
                                    lastLocation.getTime()));
                } else {
                    locationTextView.setText(R.string.no_location);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private static class FetchAddressTask extends AsyncTask<Location, Void, String> {

        private static final String TAG = "FetchAddressTask";
        private final WeakReference<Context> context;

        public FetchAddressTask(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(context.get(), Locale.getDefault());
            Location location = locations[0];
            List<Address> addressList;
            String result;

            try {
                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addressList == null || addressList.size() == 0) {
                    result = context.get().getString(R.string.no_address_found);
                } else {
                    Address address = addressList.get(0);
                    List<String> addressParts = new ArrayList<>();

                    for (Address addressPart : addressList) {
                        addressParts.add(addressPart.toString());
                    }
                    result = TextUtils.join("\n", addressParts);
                }
            } catch (IOException ioException) {
                result = context.get().getString(R.string.service_not_available);
                Log.e(TAG, "doInBackground: " + result + ": ", ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                result = context.get().getString(R.string.invalid_lat_long_used);
                Log.e(TAG, "doInBackground: " + result +
                        ": \nLatitude = " + location.getLatitude() +
                        "\nLongitude = " + location.getLongitude(), illegalArgumentException);
            }

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String address) {
            super.onPostExecute(address);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
