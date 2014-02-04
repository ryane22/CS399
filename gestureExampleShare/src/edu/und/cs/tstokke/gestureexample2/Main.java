package edu.und.cs.tstokke.gestureexample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnGestureListener {
	
	private GestureDetector gDetector;
	LinearLayout layout2;
	boolean textView2Invisible = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        gDetector = new GestureDetector(this);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
    }

    public boolean onTouchEvent(MotionEvent me) {
    	return gDetector.onTouchEvent(me);
    }
    
	@Override
	public boolean onDown(MotionEvent arg0) {
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
		if (deltaX > deltaY) { //more left or right than up or down
			if (changeX < 0) {
				Intent newIntent = new Intent(Main.this, LeftActivity.class);
				startActivity(newIntent);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
			else {
				Intent newIntent = new Intent(Main.this, RightActivity.class);
				startActivity(newIntent);
			}
		}
		else { //more up or down than left or right
			if (changeY < 0 && !textView2Invisible) { //swipe down and can see the lower layout
				layout2.setVisibility(View.GONE);
				textView2Invisible = true;
			}
			else if (changeY > 0 && textView2Invisible) { //swipe up and can't see the lower layout
				layout2.setVisibility(View.VISIBLE);
				textView2Invisible = false;
			}
		}
		
		
		return false;
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