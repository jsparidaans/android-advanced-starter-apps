package com.example.android.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(v -> finish());
    }
}