/************************************************************/
/** Ryan Evenstad                                          **/
/** Created March 6, 2012                                  **/
/** This is a simple contacts app that allows the user to  **/
/** set contacts and choose that contact to call. All      **/
/** contacts are saved in a data file to insure data is    **/
/** retained when the app is closed.                       **/
/************************************************************/

package edu.cs.und.revenstad.lab3;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnLongClickListener{
	Button btnContact1;
	Button btnContact2;
	Button btnContact3;
	Button btnContact4;
	
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor myEditor;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnContact1 = (Button)findViewById(R.id.btnContact1);
		btnContact2 = (Button)findViewById(R.id.btnContact2);
		btnContact3 = (Button)findViewById(R.id.btnContact3);
		btnContact4 = (Button)findViewById(R.id.btnContact4);
		
		btnContact1.setOnLongClickListener(this);
		btnContact2.setOnLongClickListener(this);
		btnContact3.setOnLongClickListener(this);
		btnContact4.setOnLongClickListener(this);
		
		//initialize objects and settings file
		mySharedPreferences = getSharedPreferences("MySettings", Activity.MODE_PRIVATE);
		myEditor = mySharedPreferences.edit();
		//does the settings file exist?
		if (mySharedPreferences != null)
		{
			//Toast.makeText(getApplicationContext(), "Found preference file", Toast.LENGTH_SHORT).show();
			//set contacts
			initialize();
		}
		else
		{
			//Toast.makeText(getApplicationContext(), "No preference file", Toast.LENGTH_SHORT).show();
		}
	}

	public void initialize(){
		if (!mySharedPreferences.getString("name1", "Default_").equals("Default_"))
		{
			String name = mySharedPreferences.getString("name1", "Default_");
			btnContact1.setText(name);
			if (mySharedPreferences.getInt("check1", 0) == 1)
			{
				String number = mySharedPreferences.getString("number1", "0");
				btnContact1.setText(btnContact1.getText().toString()+"\n"+number);
			}
		}
		if (!mySharedPreferences.getString("name2", "Default_").equals("Default_"))
		{
			String name = mySharedPreferences.getString("name2", "Default_");
			btnContact2.setText(name);
			if (mySharedPreferences.getInt("check2", 0) == 1)
			{
				String number = mySharedPreferences.getString("number2", "0");
				btnContact2.setText(btnContact2.getText().toString()+"\n"+number);
			}
		}
		if (!mySharedPreferences.getString("name3", "Default_").equals("Default_"))
		{
			String name = mySharedPreferences.getString("name3", "Default_");
			btnContact3.setText(name);
			if (mySharedPreferences.getInt("check3", 0) == 1)
			{
				String number = mySharedPreferences.getString("number3", "0");
				btnContact3.setText(btnContact3.getText().toString()+"\n"+number);
			}
		}
		if (!mySharedPreferences.getString("name4", "Default_").equals("Default_"))
		{
			String name = mySharedPreferences.getString("name4", "Default_");
			btnContact4.setText(name);
			if (mySharedPreferences.getInt("check4", 0) == 1)
			{
				String number = mySharedPreferences.getString("number4", "0");
				btnContact4.setText(btnContact4.getText().toString()+"\n"+number);
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return true;
	}
	
	public void placeCall(View v){
		if (v.getId() == btnContact1.getId())
		{
			if (!mySharedPreferences.getString("name1", "Default_").equals("Default_"))
			{
				String number = mySharedPreferences.getString("number1", "0");
				String phoneNumber = "tel:" + number;
				Intent myActivity = new Intent (Intent.ACTION_CALL, Uri.parse(phoneNumber));
				startActivity(myActivity);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Set A Contact", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.getId() == btnContact2.getId())
		{
			if (!mySharedPreferences.getString("name2", "Default_").equals("Default_"))
			{
				String number = mySharedPreferences.getString("number2", "0");
				String phoneNumber = "tel:" + number;
				Intent myActivity = new Intent (Intent.ACTION_CALL, Uri.parse(phoneNumber));
				startActivity(myActivity);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Set A Contact", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.getId() == btnContact3.getId())
		{
			if (!mySharedPreferences.getString("name3", "Default_").equals("Default_"))
			{
				String number = mySharedPreferences.getString("number3", "0");
				String phoneNumber = "tel:" + number;
				Intent myActivity = new Intent (Intent.ACTION_CALL, Uri.parse(phoneNumber));
				startActivity(myActivity);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Set A Contact", Toast.LENGTH_SHORT).show();
			}
		}
		else if (v.getId() == btnContact4.getId())
		{
			if (!mySharedPreferences.getString("name4", "Default_").equals("Default_"))
			{
				String number = mySharedPreferences.getString("number4", "0");
				String phoneNumber = "tel:" + number;
				Intent myActivity = new Intent (Intent.ACTION_CALL, Uri.parse(phoneNumber));
				startActivity(myActivity);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Please Set A Contact", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		//call new activity and get text back...
		//create intent that will launch the new activity
		Intent myIntent = new Intent(this, getInfo.class);
		//create a bundle to store the data that needs to be sent to the activity
		Bundle dataBundle = new Bundle();
		
		if (v.getId() == btnContact1.getId())
		{
			dataBundle.putInt("contactNum", 1);
			myIntent.putExtras(dataBundle);
			startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact2.getId())
		{
			dataBundle.putInt("contactNum", 2);
			myIntent.putExtras(dataBundle);
			startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact3.getId())
		{
			dataBundle.putInt("contactNum", 3);
			myIntent.putExtras(dataBundle);
			startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact4.getId())
		{
			dataBundle.putInt("contactNum", 4);
			myIntent.putExtras(dataBundle);
			startActivityForResult(myIntent, 1);
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK)
		{
			Bundle dataBundle = data.getExtras();
			int result = dataBundle.getInt("result", 0);
			set(result);
		}
	}
	
	public void set(int num){
		if (num == 1)
		{
			String name = mySharedPreferences.getString("name1", "Default_");
			btnContact1.setText(name);
			if (mySharedPreferences.getInt("check1", 0) == 1)
			{
				String number = mySharedPreferences.getString("number1", "0");
				btnContact1.setText(btnContact1.getText().toString()+"\n"+number);
			}
		}
		else if (num == 2)
		{
			String name = mySharedPreferences.getString("name2", "Default_");
			btnContact2.setText(name);
			if (mySharedPreferences.getInt("check2", 0) == 1)
			{
				String number = mySharedPreferences.getString("number2", "0");
				btnContact2.setText(btnContact2.getText().toString()+"\n"+number);
			}
		}
		else if (num == 3)
		{
			String name = mySharedPreferences.getString("name3", "Default_");
			btnContact3.setText(name);
			if (mySharedPreferences.getInt("check3", 0) == 1)
			{
				String number = mySharedPreferences.getString("number3", "0");
				btnContact3.setText(btnContact3.getText().toString()+"\n"+number);
			}
		}
		else if (num == 4)
		{
			String name = mySharedPreferences.getString("name4", "Default_");
			btnContact4.setText(name);
			if (mySharedPreferences.getInt("check4", 0) == 1)
			{
				String number = mySharedPreferences.getString("number4", "0");
				btnContact4.setText(btnContact4.getText().toString()+"\n"+number);
			}
		}
		else
			Toast.makeText(getApplicationContext(), "not good", Toast.LENGTH_SHORT).show();
	}
}
