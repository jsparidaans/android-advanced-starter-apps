package com.dhl_myid.surfaceviewexample.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Context context;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Path path;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        path = new Path();
    }

    public void pause() {
        //stub
    }

    public void resume() {
        //stub
    }

    @Override
    public void run() {
        //stub
    }

}
