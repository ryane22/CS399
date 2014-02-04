package edu.und.cs.revenstad.spinnerexample;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {
	Button btnShowInfo;
	Spinner spnMonth;
	Spinner spnWeekday;
	Spinner spnDay;
	TextView txtInfo;
	ArrayList<String> validDates;
	ArrayAdapter<String> spinnerArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnShowInfo = (Button) findViewById(R.id.btnShowInfo);
		spnMonth = (Spinner) findViewById(R.id.spnMonth);
		spnWeekday = (Spinner) findViewById(R.id.spnWeekday);
		spnDay = (Spinner) findViewById(R.id.spnDay);
		txtInfo = (TextView) findViewById(R.id.txtInfo);
		
		//create the ArrayList
		validDates = new ArrayList<String>();
		//set up data in an ArrayList
		for (int d=1; d<=31; ++d)
			validDates.add(Integer.toString(d)); //or validDates.add(String.valueOf(d));
		//apply data to an ArrayAdapter
		spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, validDates);
		//describe how the drop down list will look
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnDay.setAdapter(spinnerArrayAdapter);
		
		btnShowInfo.setOnClickListener(this);
		
		spnMonth.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		String selectedMonth = spnMonth.getSelectedItem().toString();
		int selectedIndex = spnMonth.getSelectedItemPosition();
		
		String output = selectedMonth;
		output += "\n\n" + "It's a " + spnWeekday.getSelectedItem().toString() + " (maybe)";
		output += "\nThe selected index for the month is " + selectedIndex;
		txtInfo.setText(output);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//arg0 is the spinner widget
		//arg2 is the index of the selected item aka int selectedIndex = spnMonth.getSelectedItemPosition();
		if (arg0.getId() == spnMonth.getId())
		{
			int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
			int days = daysInMonth[arg2];
			int currentSize = validDates.size();
			if (currentSize < days)
				for (int d=currentSize; d<=days; ++d)
					validDates.add(String.valueOf(d));
			else
				for (int d=currentSize; d>days; --d)
				{
					currentSize--;
					validDates.remove(currentSize);
				}
			spinnerArrayAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//do nothing here, there will never be a time in our app when no item is selected
	}

}
