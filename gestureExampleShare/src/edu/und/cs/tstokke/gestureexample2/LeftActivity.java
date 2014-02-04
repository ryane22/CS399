package edu.und.cs.tstokke.gestureexample2;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class LeftActivity extends Activity implements OnGestureListener {

	private GestureDetector gDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_left);
        gDetector = new GestureDetector(this);
	}

	public void btnGoToMain (View v)
	{
		finish();
	}

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent start, MotionEvent finish, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		float changeX = start.getRawX() - finish.getRawX();
		float changeY = start.getRawY() - finish.getRawY();
		float deltaX = Math.abs(changeX);
		float deltaY = Math.abs(changeY);
		
		//Toast.makeText(this, "change x " + changeX, Toast.LENGTH_LONG).show();
		//Toast.makeText(this, "change y " + changeY, Toast.LENGTH_LONG).show();
		if ((deltaX > deltaY) && (changeX > 0)) 
    		finish();
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
