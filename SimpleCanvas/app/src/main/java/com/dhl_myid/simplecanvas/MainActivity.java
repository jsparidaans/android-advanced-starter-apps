package com.dhl_myid.simplecanvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    //Custom objects
    private Canvas canvas;
    private Paint paint,paintText;
    private Bitmap bitmap;
    private Rect rect,bounds;

    //Constants
    private static final int OFFSET= 120;
    private static final int MULTIPLIER= 100;
    private int offset = OFFSET;

    //Colors
    private int colorBackground;
    private int colorRectangle;
    private int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imageView = findViewById(R.id.imageView);
        paint.setColor(colorBackground);
        paintText.setColor(ResourcesCompat.getColor(getResources(),R.color.design_default_color_primary_dark,null));
        paintText.setTextSize(70);

    }
    public void drawSomething(View view){

    }

    private void init(){
        //Initialize paint
        paint = new Paint();
        paintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

        //Initialize colors
        colorBackground= ResourcesCompat.getColor(getResources(),R.color.colorBackground,null);
        colorBackground= ResourcesCompat.getColor(getResources(),R.color.colorRectangle,null);
        colorBackground= ResourcesCompat.getColor(getResources(),R.color.teal_700,null);
    }
}