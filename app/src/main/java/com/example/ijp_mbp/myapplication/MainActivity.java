package com.example.ijp_mbp.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private CalcLogic calcLogic;
    private OrientationHandler handler;

    private static final int SENSOR_DELAY = 500 * 1000; // 500ms

    private View getButtonFromCode(int code) {
        String buttonID = "b" + String.valueOf(code);
        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
        return findViewById(resID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = getButtonFromCode(10);
        TextView textView = (TextView) findViewById(R.id.Calc);
        calcLogic = new CalcLogic(this, view, textView);
        handler = new OrientationHandler();

        SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sm.registerListener(this, sensor, SENSOR_DELAY);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            handler.update(event.values);
            int code = handler.getCurrentButton();
            Log.i("Button", String.valueOf(code));
            buttonHighlight(code);
        }
    }

    public void buttonHighlight(int code){
        View view = getButtonFromCode(code);
        calcLogic.calcView.setCellActive(view);
    }

    public void buttonClick(View view){
        calcLogic.handleButtonClick(view);
    }
}
