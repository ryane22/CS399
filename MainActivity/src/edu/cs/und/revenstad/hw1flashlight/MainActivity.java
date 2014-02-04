/***********************/
/* Ryan Evenstad Lab 1 */
/***********************/


package edu.cs.und.revenstad.hw1flashlight;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
//import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener{
	//initialize buttons
	Button btnWhite;
	Button btnBlue;
	Button btnGreen;
	Button btnRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set buttons to their respective id's
        btnWhite = (Button)findViewById(R.id.btnWhite);
        btnBlue = (Button)findViewById(R.id.btnBlue);
        btnGreen = (Button)findViewById(R.id.btnGreen);
        btnRandom = (Button)findViewById(R.id.btnRandom);
        //set onClickListeners for each button
        btnWhite.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnRandom.setOnClickListener(this);
        
    }
    
    public void onClick(View v) {
    	//change background color
    	if (v.getId() == btnWhite.getId())
		{
    		getWindow().getDecorView().setBackgroundColor(Color.WHITE);
		}
    	else if (v.getId() == btnBlue.getId())
		{
    		getWindow().getDecorView().setBackgroundColor(Color.BLUE);
		}
    	else if (v.getId() == btnGreen.getId())
		{
    		getWindow().getDecorView().setBackgroundColor(Color.GREEN);
		}
    	else if (v.getId() == btnRandom.getId())
		{
    		getWindow().getDecorView().setBackgroundColor(Color.RED);
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
