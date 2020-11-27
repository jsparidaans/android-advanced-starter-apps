package com.example.android.fragmentexample;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecondActivity extends AppCompatActivity {
    private Button previousButton, mButton;
    private boolean isFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(v -> {
            if (!isFragmentDisplayed) {
                displayFragment();
            } else {
                closeFragment();
            }
        });

        previousButton = findViewById(R.id.previous_button);
        previousButton.setOnClickListener(v -> finish());
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance("", "");
        //Get the fragment manager to start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the fragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        //Update button text
        mButton.setText(R.string.close);

        //set boolean open indicator
        isFragmentDisplayed = true;


    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }
}