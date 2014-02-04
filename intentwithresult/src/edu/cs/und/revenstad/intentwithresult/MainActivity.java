package edu.cs.und.revenstad.intentwithresult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText edtValue1;
	EditText edtValue2;
	TextView txtSum;
	final int ADD_NUMBERS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtValue1 = (EditText)findViewById(R.id.edtValue1);
		edtValue2 = (EditText)findViewById(R.id.edtValue2);
		txtSum = (TextView)findViewById(R.id.txtSum);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void btnAddValues (View arg0){
		Toast.makeText(getApplicationContext(), "LaunchSecondActivity", Toast.LENGTH_SHORT).show();
		Double val1 = Double.parseDouble("0"+edtValue1.getText().toString());//don't add the 0+ to anything real
		Double val2 = Double.parseDouble("0"+edtValue2.getText().toString());
		
		//create intent that will launch the new activity
		Intent myIntent = new Intent(this, AddingActivity.class);
		//create a bundle to store the data that needs to be sent to the activity
		Bundle dataBundle = new Bundle();
		//add data to the bundle
		dataBundle.putDouble("val1", val1);
		dataBundle.putDouble("val2", val2);
		dataBundle.putString("name", "Ryan");
		//attach bundle to the intent
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, ADD_NUMBERS); //add_numbers is the request code and is an "arbitrary" integer
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try
		{
			if ((requestCode == ADD_NUMBERS) && (resultCode == Activity.RESULT_OK))
			{
				Bundle dataBundle = data.getExtras();
				Double result = dataBundle.getDouble("result");
				txtSum.setText("Sum is " + result);
			}
		}
		catch(Exception e)
		{
			txtSum.setText("Problem with the other activity");
		}
	}
}
