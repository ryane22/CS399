package edu.und.cs.tstokke.rollingballtarget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BallView extends View {

	public float x;
    public float y;
    private final int radius;
    private final Paint ballStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint barStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    //construct new ball object
    public BallView(Context context, float x, float y, int radius) {
        super(context);
        //color hex is [transparency][red][green][blue]
        ballStyle.setColor(0xFF00FF00); //not transparent. color is green
        barStyle.setColor(0xFF0000FF);
        this.x = x;
        this.y = y;
        this.radius = radius; 
    }
    	
    //called by invalidate()	
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, ballStyle);
        canvas.drawRect(200, 200, 280, 800, barStyle);
    } 
}
