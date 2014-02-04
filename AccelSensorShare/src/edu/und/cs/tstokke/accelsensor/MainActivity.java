package edu.und.cs.tstokke.accelsensor;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {
	
    TextView txtXValue;
    TextView txtYValue;
    TextView txtZValue;
    SensorManager accelSensorManager;
    Sensor accelSensor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtXValue = (TextView) findViewById (R.id.txtXValue);
        txtYValue = (TextView) findViewById (R.id.txtYValue);
        txtZValue = (TextView) findViewById (R.id.txtZValue);

        ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener (
        		new SensorEventListener() {

					@Override
					public void onAccuracyChanged(Sensor arg0, int arg1) {
					}

					@Override
					public void onSensorChanged(SensorEvent event) {
						// TODO Auto-generated method stub
						txtXValue.setText(""+event.values[0]);
						txtXValue.setText(""+event.values[1]);
						txtXValue.setText(""+ (event.values[2]));
					}
        			
        		},
        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
        
        
    } //OnCreate
    
}