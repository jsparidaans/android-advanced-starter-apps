package com.example.android.walkmyandroid;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FetchAddressTask extends AsyncTask<Location, Void, String> {

    private static final String TAG = "FetchAddressTask";
    private final WeakReference<Context> context;
    private final OnTaskCompleted delegate;

    public FetchAddressTask(Context context, OnTaskCompleted delegate) {
        this.context = new WeakReference<>(context);
        this.delegate = delegate;
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
                ArrayList<String> addressParts = new ArrayList<>();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressParts.addAll(Collections.singleton(address.getAddressLine(i)));
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
        delegate.onTaskCompleted(address);
        super.onPostExecute(address);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    interface OnTaskCompleted {
        void onTaskCompleted(String result);
    }
}
