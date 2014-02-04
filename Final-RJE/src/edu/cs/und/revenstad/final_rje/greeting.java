package edu.cs.und.revenstad.final_rje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class greeting extends Activity {
	TextView txtGreeting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.greeting);
		
		txtGreeting = (TextView)findViewById(R.id.txtGreeting);
		
		Intent localIntent = getIntent();
		Bundle dataBundle = localIntent.getExtras();
		String fName = dataBundle.getString("firstName");
		String lName = dataBundle.getString("lastName");
		
		txtGreeting.setText("Hello, " + fName + " " + lName +"!");
	}
	
	public void goBack(View v) {
		finish();
	}
}
