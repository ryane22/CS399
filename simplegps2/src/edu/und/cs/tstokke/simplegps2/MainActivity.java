package edu.und.cs.tstokke.simplegps2;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private LocationManager locationManager;
    private TextView textView;
    private TextView txtGPSLat;
    private TextView txtGPSLong;
    private TextView txtNetworkLat;
    private TextView txtNetworkLong;
    private TextView txtGPSStatus;
    private TextView txtNetworkStatus;
    private Button btnGetLocation;
    private Button btnShowMap;
    private double latitude;
    private double longitude;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        //set up button activities
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private final LocationListener gpsLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
            case LocationProvider.AVAILABLE:
                txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nGPS available again");
                break;
            case LocationProvider.OUT_OF_SERVICE:
            	txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nGPS out of service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
            	txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nGPS temporarily unavailable");
                break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nGPS Provider Enabled\n");
        }

        @Override
        public void onProviderDisabled(String provider) {
            txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nGPS Provider Disabled\n");
        }

        @Override
        public void onLocationChanged(Location location) {
            //locationManager.removeUpdates(networkLocationListener);
            txtGPSStatus.setText(txtGPSStatus.getText().toString() + "\nNew GPS location:" + location.getTime());
	        txtGPSLat.setText(String.format("%9.6f", location.getLatitude()));
	        txtGPSLong.setText(String.format("%9.6f", location.getLongitude()));
	        latitude = location.getLatitude();
	        longitude = location.getLongitude();

        }
    };

	private final LocationListener networkLocationListener = new LocationListener() 
	{

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        switch (status) {
	        case LocationProvider.AVAILABLE:
	            txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNetwork location available again");
	            break;
	        case LocationProvider.OUT_OF_SERVICE:
	        	txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNetwork location out of service");
	            break;
	        case LocationProvider.TEMPORARILY_UNAVAILABLE:
	        	txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNetwork location temporarily unavailable");
	            break;
	        }
	    }
        @Override
        public void onProviderEnabled(String provider) {
        	txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNetwork Provider Enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
        	txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNetwork Provider Disabled");
        }

        @Override
        public void onLocationChanged(Location location) {
        	txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nNew network location: " + location.getTime());
        	txtNetworkLat.setText(String.format("%9.6f", location.getLatitude()));
        	txtNetworkLong.setText(String.format("%9.6f", location.getLongitude()));
	        latitude = location.getLatitude();
	        longitude = location.getLongitude();
        }

    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGPSLat = (TextView) findViewById(R.id.txtGPSLat);
        txtGPSLong = (TextView) findViewById(R.id.txtGPSLong);
        txtGPSLat.setText("GPS Lat");
        txtGPSLong.setText("GPS Long");
        txtNetworkLat = (TextView) findViewById(R.id.txtNetworkLat);
        txtNetworkLong = (TextView) findViewById(R.id.txtNetworkLong);
        txtNetworkLat.setText("GPS Lat");
        txtNetworkLong.setText("GPS Long");
        txtGPSStatus = (TextView) findViewById(R.id.txtGPSStatus);
        txtGPSStatus.setText("");
        txtNetworkStatus = (TextView) findViewById(R.id.txtNetworkStatus);
        txtNetworkStatus.setText("");
        btnGetLocation = (Button) findViewById(R.id.btnGetLocation);
        btnGetLocation.setOnClickListener(this);
        btnShowMap = (Button) findViewById(R.id.btnShowMap);
        btnShowMap.setOnClickListener(this);
        latitude = 12345;
        longitude = 12345;
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, networkLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, gpsLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(networkLocationListener);
        locationManager.removeUpdates(gpsLocationListener);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static boolean isConnectedMobile(Context context) {
		NetworkInfo info = Connectivity.getNetworkInfo(context);
		return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == btnGetLocation.getId())
		{
           isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
           if (isConnectedMobile(this))
        	   txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nMobile connected");
           else
        	   txtNetworkStatus.setText(txtNetworkStatus.getText().toString() + "\nMobile not connected");

           isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
           if (!isGPSEnabled && !isNetworkEnabled) 
       		    showSettingsAlert();

       	   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, networkLocationListener);
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, gpsLocationListener);

		}
		else if (arg0.getId() == btnShowMap.getId())
		{
//				// this looks good on a big screen		
//				String myGeoCode = "https://maps.google.com/maps?q="
//				           + latitude
//				           + ","
//				           + longitude
//				           + "(You are here!)&iwloc=A&hl=en";
				
			// this looks better on a small screen
			if (latitude != 12345)
			{
			   String myGeoCode = "geo:" + latitude + "," + longitude + "?z=15";
			   Intent intentViewMap = new Intent(Intent.ACTION_VIEW, Uri.parse(myGeoCode));
			   startActivity(intentViewMap);
			}
			else
				Toast.makeText(this, "Unable to determine location", Toast.LENGTH_LONG).show();
		}
	}

}
