package com.dhl_myid.surfaceviewexample.model;

public class FlashLightCone {
    private int x, y, radius;

    public FlashLightCone(int viewWidth, int viewHeight) {
        x = viewWidth / 2;
        y = viewHeight / 2;
        radius = ((viewWidth <= viewHeight) ? x / 3 : y / 3);
    }

    public void update(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
}
