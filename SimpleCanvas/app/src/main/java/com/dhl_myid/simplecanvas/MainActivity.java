package com.dhl_myid.simplecanvas;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    //Custom objects
    private Canvas canvas;
    private Paint paint, paintText;
    private Bitmap bitmap;
    private Rect rect, bounds;

    //Constants
    private static final int OFFSET = 120;
    private static final int MULTIPLIER = 100;
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
        paintText.setColor(ResourcesCompat.getColor(getResources(), R.color.design_default_color_primary_dark, null));
        paintText.setTextSize(70);

    }

    public void drawSomething(View view) {
        //Initialize variables
        int width, height, halfWidth, halfHeight;
        width = view.getWidth();
        height = view.getHeight();
        halfHeight = height / 2;
        halfWidth = width / 2;

        if (offset == OFFSET) {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            imageView.setImageBitmap(bitmap);
            canvas = new Canvas(bitmap);
            canvas.drawColor(colorBackground);
            canvas.drawText(getString(R.string.keep_tapping), 100, 100, paintText);
            offset += OFFSET;
        } else {
            if (offset < halfWidth && offset < halfHeight) {
                paint.setColor(colorRectangle - MULTIPLIER * offset);
                rect.set(offset, offset, width - offset, height - offset);
                canvas.drawRect(rect, paint);
                offset += OFFSET;
            } else {
                paint.setColor(colorAccent);
                canvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, paint);
                String text = getString(R.string.done);
                paintText.getTextBounds(text, 0, text.length(), bounds);

                int x = halfWidth - bounds.centerX();
                int y = halfHeight - bounds.centerY();
                canvas.drawText(text, x, y, paintText);
            }
        }
        view.invalidate();
    }

    private void init() {
        //Initialize paint
        paint = new Paint();
        paintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

        //Initialize shapes
        rect = new Rect();
        bounds = new Rect();

        //Initialize colors
        colorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        colorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        colorAccent = ResourcesCompat.getColor(getResources(), R.color.teal_700, null);
    }
}