package edu.cs.und.revenstad.intentwithresult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddingActivity extends Activity implements OnClickListener{
	EditText edtDataReceived;
	Button btnDone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adding);
		
		edtDataReceived = (EditText)findViewById(R.id.edtDataReceived);
		btnDone = (Button)findViewById(R.id.btnDone);
		btnDone.setOnClickListener(this);
		
		//to return data to the calling activity
		//get the original intent sent to this activity
		Intent localIntent = getIntent();
		//grab the data we need with the various get methods
		Bundle dataBundle = localIntent.getExtras();
		Double val1 = dataBundle.getDouble("val1");
		Double val2 = dataBundle.getDouble("val2");
		String name = dataBundle.getString("name");
		//do what we need to do with the data
		Double result = val1 + val2;
		
		edtDataReceived.setText("Hey " + name + " the result of adding " + val1 + " and " + val2 + " is " + result);
		//if needed put any new data or updated data back into the bundle
		dataBundle.putDouble("result", result);
		//add the bundle get into the intent
		localIntent.putExtras(dataBundle);
		//return the information back to the calling activity
		setResult(Activity.RESULT_OK, localIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_adding, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

}
