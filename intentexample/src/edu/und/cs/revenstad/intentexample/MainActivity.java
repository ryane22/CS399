package edu.und.cs.revenstad.intentexample;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	Button btnStartCall;
	Button btnMakeCall;
	Button btnStartText;
	Button btnMakeText;
	EditText edtNumber;
	EditText edtMessage;
	EditText edtURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnStartCall = (Button)findViewById(R.id.btnStartCall);
		btnMakeCall = (Button)findViewById(R.id.btnMakeCall);
		btnStartText = (Button)findViewById(R.id.btnStartText);
		btnMakeText = (Button)findViewById(R.id.btnMakeText);
		edtNumber = (EditText)findViewById(R.id.edtNumber);
		edtMessage = (EditText)findViewById(R.id.edtMessage);
		edtURL = (EditText)findViewById(R.id.edtURL);
		
		btnStartCall.setOnClickListener(this);
		btnMakeCall.setOnClickListener(this);
		btnStartText.setOnClickListener(this);
		btnMakeText.setOnClickListener(this);
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
		if (v.getId() == btnStartCall.getId())
		{		
			String phoneNumber = "tel:" + edtNumber.getText().toString();
			Intent myActivity = new Intent (Intent.ACTION_DIAL, Uri.parse(phoneNumber));
			startActivity(myActivity);
		}
		else if (v.getId() == btnMakeCall.getId())
		{
			String phoneNumber = "tel:" + edtNumber.getText().toString();
			Intent myActivity = new Intent (Intent.ACTION_CALL, Uri.parse(phoneNumber));
			startActivity(myActivity);
		}
		else if (v.getId() == btnStartText.getId())
		{
			String phoneNumber = "smsto:" + edtNumber.getText().toString();
			Intent myActivity = new Intent (Intent.ACTION_SENDTO, Uri.parse(phoneNumber));
			myActivity.putExtra("sms_body", edtMessage.getText().toString());
			startActivity(myActivity);
		}
		else if (v.getId() == btnMakeText.getId())
		{
			String phoneNumber = edtNumber.getText().toString();
			String message = edtMessage.getText().toString();
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNumber, null, message, null, null);
			Toast.makeText(getApplication(), "Message Sent", Toast.LENGTH_SHORT).show();
		}
	}

	public void goToWebsite (View arg0) {
		String myData = edtURL.getText().toString().trim();
		if (!myData.substring(0, 6).equalsIgnoreCase("http://"))
			myData = "http://" + myData;
		try
		{
			Intent myActivity = new Intent (Intent.ACTION_VIEW, Uri.parse(myData));
			startActivity(myActivity);
			Toast.makeText(getApplication(), "Launching Website", Toast.LENGTH_SHORT).show();
		}
		catch (Exception e)
		{
			Toast.makeText(getApplication(), "Invalid URL", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void goToMaps (View arg0) {
		//just to see what arg0 is...
		Button btnMaps = (Button)findViewById(R.id.btnMaps);
		if (arg0.getId() == btnMaps.getId())
			Toast.makeText(getApplication(), "Maps Button", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplication(), "Not Maps Button", Toast.LENGTH_SHORT).show();
		
		//String geoString = "geo:40,49";
		String geoString = "http://maps.google.com/maps?47.55,-97.1";
		Intent myActivity = new Intent (Intent.ACTION_VIEW, Uri.parse(geoString));
		startActivity(myActivity);
	}
}
