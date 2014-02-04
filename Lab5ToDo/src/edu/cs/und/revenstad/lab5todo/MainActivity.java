/*********************************/
/* Ryan Evenstad Lab 5 CSci 399  */
/* ryan.evenstad22@gmail.com     */
/* Written between 4/23 and 4/25 */
/* A basic ToDo app that is      */
/* implemented using a database  */
/*********************************/

package edu.cs.und.revenstad.lab5todo;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
//import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
import android.view.View;
//import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {
	ListView listItems;
	Button add;
	SQLiteDatabase db;
	private ToDoDataSource datasource;
	ArrayAdapter<TODO> adapter;
	List<TODO> values;
	String tName;
	String tDesc;
	String tDate;
	int plac;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listItems = (ListView)findViewById(R.id.listView1);
		add = (Button)findViewById(R.id.btnAdd);
		
		listItems.setOnItemClickListener(this);
		listItems.setOnItemLongClickListener(this);
		
		datasource = new ToDoDataSource(this);
	    datasource.open();
	    
	    values = datasource.getAllComments();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    adapter = new ArrayAdapter<TODO>(this,
	        android.R.layout.simple_list_item_1, values);
	    listItems.setAdapter(adapter);
	    
	}
	
	private void save(Intent data) {
		Bundle dataBundle = data.getExtras();
		String taskName = dataBundle.getString("taskName");
		String description = dataBundle.getString("description");
		int dueMonth = dataBundle.getInt("dueMonth");
		int dueDay = dataBundle.getInt("dueDay");
		int dueYear = dataBundle.getInt("dueYear");
		String dueDate = String.valueOf(dueMonth) +"/"+String.valueOf(dueDay)+"/"+String.valueOf(dueYear);

		TODO task = datasource.createTask(taskName, description, dueDate);
		adapter.add(task);
	}
	
	private void delete(Intent data) {
		Bundle dataBundle = data.getExtras();
		int place = dataBundle.getInt("place");
		TODO task = values.get(place);
		datasource.deleteTask(task);
		adapter.remove(task);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK)
		{	
			Bundle dataBundle = data.getExtras();
			int fromSave = dataBundle.getInt("fromSave", 0);
			if (fromSave == 1) { //coming from save
				if (dataBundle.getInt("isNotEdit") == 1)
					save(data);
				else {
					delete(data);
					save(data);
				}
			}
			else {
				delete(data);
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	
	public void addItem(View v) {
		Intent myIntent = new Intent(this, todoedit.class);
		Bundle dataBundle = new Bundle();
		dataBundle.putInt("isAdd", 1);
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 1);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//Toast.makeText(this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
		List<String> row = datasource.getRow(values.get(arg2).toString());
		
//		Toast.makeText(getApplicationContext(), "row is " 
//			    + row.get(0)
//			    + row.get(1)
//			    + row.get(2), Toast.LENGTH_SHORT).show();
		
		Intent myIntent = new Intent(this, todoedit.class);
		Bundle dataBundle = new Bundle();
		dataBundle.putInt("isAdd", 0);
		dataBundle.putString("taskName", row.get(0));
		dataBundle.putString("description", row.get(1));
		dataBundle.putString("dueDate", row.get(2));
		dataBundle.putInt("place", arg2);
		
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 1);
		
		return false;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		List<String> row = datasource.getRow(values.get(arg2).toString());
		tName = row.get(0);
		tDesc = row.get(1);
		tDate = row.get(2);
		plac = arg2;
		
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		builder = new AlertDialog.Builder(this);
		builder.setTitle(tName);
		builder.setMessage(tDesc + "\n\nDue on\n" + tDate);
		
		builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//do nothing
			}
		});
		builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getApplicationContext(), todoedit.class);
				Bundle dataBundle = new Bundle();
				dataBundle.putInt("isAdd", 0);
				dataBundle.putString("taskName", tName);
				dataBundle.putString("description", tDesc);
				dataBundle.putString("dueDate", tDate);
				dataBundle.putInt("place", plac);
				
				myIntent.putExtras(dataBundle);
				startActivityForResult(myIntent, 1);
			}
		});
		builder.setCancelable(false);
		alertDialog = builder.create();
		alertDialog.show();
	}
}
