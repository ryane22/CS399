package edu.und.cs.revenstad.preferences;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	Button btnSimple;
	Button btnExtra;
	TextView txtCaption;

	
	final int mode = Activity.MODE_PRIVATE;
	final String MySettings = "MySettings";
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor myEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSimple = (Button) findViewById(R.id.btnSimple);
		btnExtra = (Button) findViewById(R.id.btnExtra);
	    txtCaption = (TextView) findViewById(R.id.txtCaption);

		
		btnSimple.setOnClickListener(this);
		btnExtra.setOnClickListener(this);
		
		//initialize objects and settings file
		mySharedPreferences = getSharedPreferences(MySettings, mode);
		myEditor = mySharedPreferences.edit();
		//does the settings file exist?
		if (mySharedPreferences != null)
		{
			//get saved info and use it
			Toast.makeText(getApplicationContext(), "Found preference file", Toast.LENGTH_SHORT).show();
			int textSize = mySharedPreferences.getInt("textSize", 20);
			txtCaption.setTextSize(textSize);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No preference file", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnSimple.getId())
		{
			txtCaption.setTextSize(24);
			myEditor.putInt("textSize", 24);
		}
		else if (v.getId() == btnExtra.getId())
		{
			txtCaption.setTextSize(30);
			myEditor.putInt("textSize", 30);
		}
		myEditor.commit();
	}
	
	@Override
	protected void onResume() {
		Toast.makeText(getApplicationContext(), "In onResume", Toast.LENGTH_SHORT).show();
		
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		Toast.makeText(getApplicationContext(), "In onStop", Toast.LENGTH_SHORT).show();
		
		super.onStop();
	}
	
	@Override
	public void onPause(){
		Toast.makeText(getApplicationContext(), "In onPause", Toast.LENGTH_SHORT).show();
		myEditor.putString("DateLastExecuted", new Date().toLocaleString());
		myEditor.commit();
		
		super.onPause();
	}

}
