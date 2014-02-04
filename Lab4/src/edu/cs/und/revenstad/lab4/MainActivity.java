/************************************************************/
/** Ryan Evenstad                                          **/
/** Created April 09, 2013                                 **/
/** This is a simple contacts app that allows the user to  **/
/** pick contacts and choose that contact to call. All     **/
/** contacts are saved in a data file to insure data is    **/
/** retained when the app is closed.                       **/
/************************************************************/

package edu.cs.und.revenstad.lab4;

import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnLongClickListener, OnItemSelectedListener{
	Button btnContact1;
	Button btnContact2;
	Button btnContact3;
	Button btnContact4;
	Spinner spinner1;
	
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor myEditor;
	
	final int SELECT_CONTACT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnContact1 = (Button)findViewById(R.id.btnContact1);
		btnContact2 = (Button)findViewById(R.id.btnContact2);
		btnContact3 = (Button)findViewById(R.id.btnContact3);
		btnContact4 = (Button)findViewById(R.id.btnContact4);
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		
		btnContact1.setOnLongClickListener(this);
		btnContact2.setOnLongClickListener(this);
		btnContact3.setOnLongClickListener(this);
		btnContact4.setOnLongClickListener(this);
		
		spinner1.setVisibility(View.GONE);
		spinner1.setOnItemSelectedListener(this);
		
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

	public static class global{
		public static int buttonNum;
		public static int checked;
		public static String contactName = null;
		public static String contactNumber = null;
		public static String numberType = null;
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
	//Intent myIntent = new Intent(this, getInfo.class);
		//create a bundle to store the data that needs to be sent to the activity
	//Bundle dataBundle = new Bundle();
		global.buttonNum = 0;
		global.checked = 0;
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
		startActivityForResult(intent, SELECT_CONTACT);
		
		if (v.getId() == btnContact1.getId())
		{
			global.buttonNum = 1;
			//dataBundle.putInt("contactNum", 1);
			//myIntent.putExtras(dataBundle);
			//startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact2.getId())
		{
			global.buttonNum = 2;
			//dataBundle.putInt("contactNum", 2);
			//myIntent.putExtras(dataBundle);
			//startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact3.getId())
		{
			global.buttonNum = 3;
			//dataBundle.putInt("contactNum", 3);
			//myIntent.putExtras(dataBundle);
			//startActivityForResult(myIntent, 1);
		}
		else if (v.getId() == btnContact4.getId())
		{
			global.buttonNum = 4;
			//dataBundle.putInt("contactNum", 4);
			//myIntent.putExtras(dataBundle);
			//startActivityForResult(myIntent, 1);
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		/*if (resultCode == Activity.RESULT_OK)
		{
			Bundle dataBundle = data.getExtras();
			int result = dataBundle.getInt("result", 0);
			set(result);
		}*/
		
			//super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == Activity.RESULT_OK)
			{
				if (requestCode == SELECT_CONTACT)
				{
					//txtContacts.setText("");
					Uri contactData = data.getData();
					@SuppressWarnings("deprecation")
					Cursor c = managedQuery(contactData, null, null, null, null);
					if (c.moveToFirst())
					{
						String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
						String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
						String output = id + " " + name + "\n";
						global.contactName = name;
						if (c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)).equals("1"))
						{
							//Toast.makeText(getApplicationContext(), "WHERE " + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, Toast.LENGTH_SHORT);
							Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
							
							List<String> list=new ArrayList<String>();
							list.add(name);
							
							while (phone.moveToNext())
							{
								String number = phone.getString(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
								int type = phone.getInt(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
								if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
								{
									output = output + "Home " + number + "\n";
									list.add("Home " + number);
								}
								else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
								{
									output = output + "Work " + number + "\n";
									list.add("Work " + number);
								}
								else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
								{
									output = output + "Mobile " + number + "\n";
									list.add("Mobile " + number);
								}
							}
							if (list.size() > 2 )
							{
								spinner1.setVisibility(View.VISIBLE);
								btnContact1.setVisibility(View.INVISIBLE);
								btnContact2.setVisibility(View.INVISIBLE);
								btnContact3.setVisibility(View.INVISIBLE);
								btnContact4.setVisibility(View.INVISIBLE);
								
								ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,
					                    android.R.layout.simple_list_item_1,list);
								adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								spinner1.setAdapter(adp1);
							}
							else
							{
								String temp = list.get(1).toString();
								//Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
								String[] parts = temp.split(" ");
								global.numberType = parts[0];
								global.contactNumber = parts[1];
								if (parts.length > 2)
									global.contactNumber += parts[2];
								
								pack(global.buttonNum);
							}
							
						}
						//btnContact1.setText(output);
					}
				}
			}
	}
	
	public void pack(int num){
		//Toast.makeText(this, num, Toast.LENGTH_SHORT).show();
		String contactNameNum = "name"+num;
		myEditor.putString(contactNameNum, global.contactName);
		String contactNumNum = "number"+num;
		myEditor.putString(contactNumNum, global.contactNumber);
		myEditor.commit();
		
		set(num);
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//arg0 is the spinner widget
		//arg2 is the index of the selected item aka int selectedIndex = spnMonth.getSelectedItemPosition();
		//Toast.makeText(this, "HEY "+ arg2, Toast.LENGTH_SHORT).show();
		if(arg2 == 0)
		{
			//Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String num = spinner1.getItemAtPosition(arg2).toString();
			String[] parts = num.split(" ");
			global.numberType = parts[0];
			global.contactNumber = parts[1];
			if (parts.length > 2)
				global.contactNumber += parts[2];
			
			spinner1.setVisibility(View.GONE);
			btnContact1.setVisibility(View.VISIBLE);
			btnContact2.setVisibility(View.VISIBLE);
			btnContact3.setVisibility(View.VISIBLE);
			btnContact4.setVisibility(View.VISIBLE);
			
			pack(global.buttonNum);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(1, 1, 1, "Show Numbers");
		menu.add(1, 2, 2, "Hide Numbers");
		return true;
	}
	private boolean handleMenuSelection(MenuItem item){
		int menuItemId = item.getItemId();
		if (menuItemId == 1)
		{
			myEditor.putInt("check1", 1);
			myEditor.putInt("check2", 1);
			myEditor.putInt("check3", 1);
			myEditor.putInt("check4", 1);
			myEditor.commit();
			initialize();
		}
		else if (menuItemId == 2)
		{
			myEditor.putInt("check1", 0);
			myEditor.putInt("check2", 0);
			myEditor.putInt("check3", 0);
			myEditor.putInt("check4", 0);
			myEditor.commit();
			initialize();
		}
		return false;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return (handleMenuSelection(item) || super.onOptionsItemSelected(item));
	}
	
}
