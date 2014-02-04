package edu.und.cs.revenstad.listexample;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener{

	ListView lstItems;
	TextView txtOutput;
	ArrayList <String> list;
	ArrayList <HashMap<String, String>> map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList <String>();
		map = new ArrayList<HashMap<String, String>>();
		for (int i = 1; i <= 12; ++i)
		{
			list.add("Item " + i);
			HashMap<String, String> newItem = new HashMap<String, String>();
			newItem.put("line1", "Item " + i);
			newItem.put("line2", "Sub item " + i);
			map.add(newItem);
		}
		
		lstItems = (ListView)findViewById(R.id.lstItems);
		txtOutput = (TextView)findViewById(R.id.txtOutput);
		
		ArrayAdapter <String> adapterSingleItem = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1,
																			android.R.id.text1, list);
		
		ArrayAdapter <String> adapterSingleItemRadio = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_single_choice,
																			android.R.id.text1, list);
		
		SimpleAdapter adapterTwoLineItem = new SimpleAdapter(this, map, android.R.layout.simple_list_item_2,
															new String[]{"line1", "line2"},
															new int[]{android.R.id.text1, android.R.id.text2});
		
		SimpleAdapter adapterCustom = new SimpleAdapter(this, map, R.layout.mycustomlayout,
														new String[]{"line1", "line2"},
														new int[]{R.id.textView1, R.id.textView2});
		
		SimpleAdapter adapterMultipleItem = new SimpleAdapter(this, map, android.R.layout.simple_list_item_multiple_choice,
																new String[] {"line1"}, new int[] {android.R.id.text1});
		
		//lstItems.setAdapter(adapterSingleItem);
		//lstItems.setAdapter(adapterSingleItemRadio);
		//lstItems.setAdapter(adapterTwoLineItem);
		//lstItems.setAdapter(adapterCustom);
		lstItems.setAdapter(adapterMultipleItem);
		//lstItems.setChoiceMode(lstItems.CHOICE_MODE_SINGLE);
		lstItems.setChoiceMode(lstItems.CHOICE_MODE_MULTIPLE);
		lstItems.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//Toast.makeText(this, "Item clicked is " + list.get(arg2) + " at index " + arg2, Toast.LENGTH_SHORT).show();
		Log.i("LISTAPP", "clicking index " + arg2);
	}
	
	public void btnShowSelected(View v)
	{
		String output;
		
		if(lstItems.getChoiceMode() == lstItems.CHOICE_MODE_SINGLE)
		{
			int index = lstItems.getCheckedItemPosition();
			if (index > -1)
			{
				output = "Selected item is " + list.get(index);
				output += "\nat index " + index;
			}
			else
				output = "No item selected";
		
			txtOutput.setText(output);
		}
		else if(lstItems.getChoiceMode() == lstItems.CHOICE_MODE_MULTIPLE)
		{
			SparseBooleanArray indexes = lstItems.getCheckedItemPositions();
			String indexesToShow = "";
			for (int i=0; i<lstItems.getCount(); i++)
			{
				if(indexes.get(i))
				{
					indexesToShow += "Index " + i + ":" + map.get(i).get("line1") + "\n";
					Log.i("LISTAPP", "index " + i + " is clicked");
				}
			}
			txtOutput.setText(indexesToShow);
		}
	}

}
