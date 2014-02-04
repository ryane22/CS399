package edu.und.cs.revenstad.selectcontact;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	final int SELECT_CONTACT = 1;
	final int SELECT_PHONE = 2;
	
	TextView txtContacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtContacts = (TextView)findViewById(R.id.txtContacts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void btnSelectContact (View v)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
		startActivityForResult(intent, SELECT_CONTACT);
	}
	
	public void btnSelectPhone (View v)
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		startActivityForResult(intent, SELECT_PHONE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK)
		{
			if (requestCode == SELECT_CONTACT)
			{
				//txtContacts.setText("");
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);
				if (c.moveToFirst())
				{
					String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
					String output = id + " " + name + "\n";
					if (c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)).equals("1"))
					{
						Toast.makeText(getApplicationContext(), "WHERE " + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, Toast.LENGTH_SHORT);
						Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
						while (phone.moveToNext())
						{
							String number = phone.getString(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
							int type = phone.getInt(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
							if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
								output = output + "Home " + number + "\n";
							else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
								output = output + "Work " + number + "\n";
							else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
								output = output + "Mobile " + number + "\n";
						}
					}
					txtContacts.setText(output);
				}
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No idea what's going on", Toast.LENGTH_SHORT).show();
		}
	}
}
