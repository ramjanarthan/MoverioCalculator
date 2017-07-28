package com.example.ijp_mbp.myapplication;

import android.hardware.SensorManager;

public class OrientationHandler {

    private float initialX;
    private float initialY;
    private float initialZ;
    private int currentButton;
    private boolean isButtonClicked;
    private boolean hasBeenClicked;

    private static final int FROM_RADS_TO_DEGS = -57;
    private static final int START_BUTTON = 10;

    private void setup (float azimuth, float pitch, float roll) {
        initialX = azimuth;
        initialY = pitch;
        initialZ = roll;
    }

    private int manageX (float azimuth) {
        float difference = initialX - azimuth;
        if (difference < -10) {
            return -1;
        } else if (difference >= -10  && difference < 5){
            return 0;
        } else if (difference >= 5 && difference < 15) {
            return 1;
        } else {
            return 2;
        }
    }

    private int manageY (float pitch) {
        float difference = initialY - pitch;
        if (difference < -14) {
            return 8;
        } else if (difference >= -14 && difference < -7) {
            return 4;
        } else if (difference > -7 && difference < 3){
            return 0;
        } else if (difference > 3 && difference < 10) {
            return -4;
        } else {
            return -8;
        }
    }

    private int manageZ (float roll) {
        float difference = initialZ - roll;
        if (Math.abs(difference) >= 17) {
            return 1;
        }  else {
            return 0;
        }
    }

    public OrientationHandler (){
        initialX = Float.MAX_VALUE;
        initialY = Float.MAX_VALUE;
        initialZ = Float.MAX_VALUE;
        currentButton = START_BUTTON;
        isButtonClicked = false;
        hasBeenClicked = false;
    }

    public int getCurrentButton (){
        return currentButton;
    }

    public boolean isButtonClicked() {
        return isButtonClicked;
    }

    public void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        float azimuth = orientation[0] * FROM_RADS_TO_DEGS;
        float pitch = orientation[1] * FROM_RADS_TO_DEGS;
        float roll = orientation[2] * FROM_RADS_TO_DEGS;

        if (initialX == Float.MAX_VALUE) {
            setup(azimuth, pitch, roll);
            return;
        }
        int xShift = manageX(azimuth);
        int yShift = manageY(pitch);
        int zShift = manageZ(roll);

        currentButton = START_BUTTON + xShift + yShift;
        if (zShift == 1 && !hasBeenClicked){
            hasBeenClicked = true;
            isButtonClicked = true;
        }
    }


}
