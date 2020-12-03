package com.dhl_myid.clippingexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dhl_myid.clippingexample.view.ClippedView;

public class MainActivity extends AppCompatActivity {

    //FIXME: Verdergaan bij 11.1 Part C
    // https://developer.android.com/codelabs/advanced-android-training-apply-clipping-to-canvas?index=..%2F..advanced-android-training&authuser=1#2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ClippedView(this));
    }
}