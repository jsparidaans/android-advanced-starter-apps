package com.dhl_myid.surfaceviewexample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dhl_myid.surfaceviewexample.R;
import com.dhl_myid.surfaceviewexample.model.FlashLightCone;


public class GameView extends SurfaceView implements Runnable {

    private Context context;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private int bitmapX, bitmapY;
    private RectF winnerRect;
    private int viewWidth;
    private int viewHeight;
    private boolean isRunning = false;
    private Thread gameThread = new Thread();
    private FlashLightCone flashLightCone;

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

    private void setUpBitmap() {
        //Set bitmap position to random positions
        bitmapX = (int) Math.floor(Math.random() * (viewWidth - bitmap.getWidth()));
        bitmapY = (int) Math.floor(Math.random() * (viewHeight - bitmap.getHeight()));

        //Bounding box
        winnerRect = new RectF(bitmapX, bitmapY,
                bitmapX + bitmap.getWidth(),
                bitmapY + bitmap.getHeight());
    }

    public void pause() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException printStackTrace) {
            printStackTrace.printStackTrace();
        }
    }

    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        //stub
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        //Get screen size
        viewWidth = width;
        viewHeight = height;

        //Create the flashlight cone
        flashLightCone = new FlashLightCone(viewWidth, viewHeight);

        //Set the text size
        paint.setTextSize(((float) viewHeight / 5));
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.android);
        setUpBitmap();
    }
}
