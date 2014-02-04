package edu.und.cs.tstokke.listandspinner;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnItemSelectedListener {

	ArrayList<String> arrayListItems;
	ArrayList<String> subArrayListItems;
	Spinner spnListItems;
	ListView lstItems;
	ArrayAdapter<String>spnItemsArrayAdapter;
	boolean ignoreSelect; //this is used to stop a from being selected when the spinner is populated
	int storedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ignoreSelect = true;
		storedItem = -1;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arrayListItems = new ArrayList<String>();
		for (int i = 1; i <= 10; ++i)
			arrayListItems.add("" + i);
		subArrayListItems = new ArrayList<String>();
		subArrayListItems.add("a");
		subArrayListItems.add("b");
		subArrayListItems.add("c");
		subArrayListItems.add("d");

	    lstItems = (ListView)findViewById(R.id.lstItems);
		lstItems.setOnItemClickListener(this);
		ArrayAdapter<String> lstItemsArrayAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, arrayListItems);
		lstItems.setChoiceMode(lstItems.CHOICE_MODE_SINGLE);
		lstItems.setAdapter(lstItemsArrayAdapter);
		
	    spnListItems = (Spinner)findViewById(R.id.spnSubItem);
		spnListItems.setOnItemSelectedListener(this);
		//ArrayAdapter<String>spnItemsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, subArrayListItems);
		spnItemsArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, subArrayListItems);
		//spnItemsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spnListItems.setSelection(-1);
		spnListItems.setAdapter(spnItemsArrayAdapter);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(!ignoreSelect)
			Toast.makeText(this, "selected " + subArrayListItems.get(arg2), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//spnListItems.setSelection(-1);
		//ignoreSelect = false;
		//spnListItems.performClick();
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		builder = new AlertDialog.Builder(this);
		builder.setTitle("Select an item");
		storedItem = -1;
		builder.setSingleChoiceItems(spnItemsArrayAdapter, -1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) { //clicking on an item in the list
				// TODO Auto-generated method stub
				storedItem = which;
			}
		});
		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (storedItem > -1)
					Toast.makeText(getApplicationContext(), "Returning " + subArrayListItems.get(storedItem), Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Nothing was selected", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
				//probably a good idea to put the stof from onClick in setPositive here
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Cancel - nothing will be returned", Toast.LENGTH_SHORT).show();
				storedItem = -1;
				
			}
		});
		builder.setCancelable(false);
		alertDialog = builder.create();
		alertDialog.show();
	}
}
