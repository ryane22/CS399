package edu.und.cs.tstokke.rollingballtarget;


import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorEventListener;


public class MainActivity extends Activity  implements SensorEventListener {
	
	BallView movingBall = null;
	Handler RedrawHandler = new Handler(); //so redraw occurs in main thread
	Timer redrawTimer = null;
	TimerTask redrawTimerTask = null;
	int screenWidth, screenHeight;
    PointF ballPosition, ballSpeed;
    FrameLayout mainView;
    int movementSpeed = 10;
    SensorManager sensorManager;
    Sensor accelSensor; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        getWindow().setFlags(0xFFFFFFFF, LayoutParams.FLAG_FULLSCREEN|LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mainView = (android.widget.FrameLayout) findViewById(R.id.main_view);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        ballPosition = new PointF();
        ballSpeed = new PointF();
        
        ballPosition.x = screenWidth / 4;
        ballPosition.y = (int)(screenHeight * 0.75);
        ballSpeed.x = 0;
        ballSpeed.y = 0;
        
        movingBall = new BallView(this, ballPosition.x, ballPosition.y, 20);
        mainView.addView(movingBall);
        movingBall.invalidate();
    }
    
    @Override
    public void onPause() // app moved to background, stop background threads
    {
    	redrawTimer.cancel();
    	redrawTimer = null;
    	redrawTimerTask = null;
    	super.onPause();
    }
    
    @Override
    public void onResume() // app moved to foreground - also occurs at startup
    {
    	redrawTimer = new Timer();
    	redrawTimerTask = new TimerTask() {
    	
    		@Override
    		public void run() {
    			//figure out where the ball should go
    			ballPosition.x += ballSpeed.x;
    			ballPosition.y += ballSpeed.y;
    			if (ballPosition.x < 0 ) ballPosition.x = 0;
    			if (ballPosition.x > screenWidth) ballPosition.x = screenWidth;
    			if (ballPosition.y < 0 ) ballPosition.y = 0;
    			if (ballPosition.y > screenHeight) ballPosition.y = screenHeight;
    			
    			if (ballPosition.x >= 200 && ballPosition.x <= 280 &&
    					ballPosition.y >= 200 && ballPosition.y <= 800)
    				runOnUiThread (new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(), "Ouch", Toast.LENGTH_SHORT).show();
						}
    				});
    			
    			movingBall.x = ballPosition.x;
    			movingBall.y = ballPosition.y;
    			// redraw the ball - run in a background thread to prevent thread lock
    			RedrawHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						movingBall.invalidate();
					}
				});
    		}
    	
    	};
    	redrawTimer.schedule(redrawTimerTask, movementSpeed, movementSpeed);
    	super.onResume();
    }// onResume
    
    @Override
    public void onDestroy() //main thread stopped
    {
    	super.onDestroy();
    	System.runFinalizersOnExit(true);                            // wait for threads to exit before clearing app
    	android.os.Process.killProcess(android.os.Process.myPid());  // remove app from memory 
    }
    
    @Override 
    public void onConfigurationChanged(Configuration newConfig)  // called when the phone wants to switch to landscape mode
	{
       super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		ballSpeed.x = -event.values[0];
		ballSpeed.y = event.values[1];
	}

}