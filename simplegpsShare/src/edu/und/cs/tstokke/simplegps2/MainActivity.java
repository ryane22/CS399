package edu.und.cs.tstokke.simplegps2;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private LocationManager locationManager;
    private TextView textView;
    private TextView txtGPSLat;
    private TextView txtGPSLong;
    private TextView txtNetworkLat;
    private TextView txtNetworkLong;
    private Button btnGetLocation;
    private Button btnShowMap;
    private Button btnStopListener;
    private double latitude;
    private double longitude;
    
    private final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //locationManager.removeUpdates(networkLocationListener);
        	txtGPSLat.setText("GPS Latitude " + String.format("%9.6f", location.getLatitude()));
        	txtGPSLong.setText("GPS Longitude " + String.format("%9.5f", location.getLongitude()));
        }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    };

	private final LocationListener networkLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        	txtNetworkLat.setText("GPS Latitude " + String.format("%9.6f", location.getLatitude()));
        	txtNetworkLong.setText("GPS Longitude " + String.format("%9.5f", location.getLongitude()));
        }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGPSLat = (TextView) findViewById(R.id.txtGPSLat);
        txtGPSLong = (TextView) findViewById(R.id.txtGPSLong);
        txtNetworkLat = (TextView) findViewById(R.id.txtNetworkLat);
        txtNetworkLong = (TextView) findViewById(R.id.txtNetworkLong);
        btnGetLocation = (Button) findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(this);
        btnShowMap = (Button) findViewById(R.id.btnShowMap);
        btnShowMap.setOnClickListener(this);
        btnStopListener = (Button)findViewById(R.id.btnStopListener);
        btnStopListener.setOnClickListener(this);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == btnGetLocation.getId()) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, gpsLocationListener);
		}
		else if (arg0.getId() == btnShowMap.getId()) {
			
		}
		else if (arg0.getId() == btnStopListener.getId()) {
			locationManager.removeUpdates(gpsLocationListener);
		}
	}

}
