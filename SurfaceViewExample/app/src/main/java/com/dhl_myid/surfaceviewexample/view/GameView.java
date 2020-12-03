package com.dhl_myid.surfaceviewexample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Context context;

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
    }

    public void pause(){
        //stub
    }

    public void resume(){
        //stub
    }

    @Override
    public void run() {
        //stub
    }

}
