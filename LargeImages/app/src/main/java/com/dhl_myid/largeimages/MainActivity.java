package com.dhl_myid.largeimages;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView imageView;
    private int toggle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this::changeImage);
    }

    public void changeImage(View view) {
        if (toggle == 0) {
            view.setBackground(ContextCompat.getDrawable(this, R.drawable.dinosaur_medium));
            toggle = 1;
        } else {
            try {
                Thread.sleep(32);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            view.setBackground(ContextCompat.getDrawable(this, R.drawable.ankylo));
            toggle = 0;
        }
    }
}