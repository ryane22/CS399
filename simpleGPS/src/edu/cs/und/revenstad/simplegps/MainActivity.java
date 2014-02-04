package edu.cs.und.revenstad.simplegps;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	Button btnGetLocation;
	Intent intentService;
	ComponentName service;
	Double latitude, longitude;
	String provider;
	TextView txtLongitude1, txtLatitude1;
	String GPS_FILTER = "cs399.action.GPS_LOCATION";
	BroadcastReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnGetLocation = (Button)findViewById(R.id.btnGetLocation);
		btnGetLocation.setOnClickListener(this);
		txtLatitude1 = (TextView)findViewById(R.id.txtLatitude1);
		txtLongitude1 = (TextView)findViewById(R.id.txtLongitude1);
		
		getLocationServiceStarted();
		
		IntentFilter myLocationFilter = new IntentFilter (GPS_FILTER);
		receiver = new MyMainLocalReceiver();
		registerReceiver(receiver, myLocationFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}

	public void getLocationServiceStarted(){
		intentService = new Intent (this, MyGpsService.class);
		service = startService (intentService);
	}
	
	private class MyMainLocalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context localContext, Intent intentResponse) {
			// TODO Auto-generated method stub
			latitude = intentResponse.getDoubleExtra("latitude", -1);
			longitude = intentResponse.getDoubleExtra("longitude", -1);
			provider = intentResponse.getStringExtra("provider");
			
			txtLatitude1.setText("" + latitude);
			txtLongitude1.setText("" + longitude);
		}
		
	}
	
	protected void onDestroy() {
		super.onDestroy();
		try {
			stopService (intentService);
			unregisterReceiver(receiver);
		}
		catch (Exception e) {
			
		}
	}
}
