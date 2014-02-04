package edu.cs.und.revenstad.finalproject;

import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class Main extends Activity implements OnItemClickListener, OnItemLongClickListener {
	ListView lstProjects;
	SQLiteDatabase db;
	private projectDataSource datasource;
	ArrayAdapter<PROJECT> adapter;
	List<PROJECT> projectNames;
	private boolean resumeHasRun = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_project);
		
		lstProjects = (ListView)findViewById(R.id.lstProjects);
		
		lstProjects.setOnItemClickListener(this);
		lstProjects.setOnItemLongClickListener(this);
		
		datasource = new projectDataSource(this);
	    datasource.open();
	    
	    projectNames = datasource.getAllProjects();

	    // change this to a custom layout later
	    adapter = new ArrayAdapter<PROJECT>(this,
	        android.R.layout.simple_list_item_1, projectNames);
	    lstProjects.setAdapter(adapter);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_project, menu);
		return true;
	}*/
	
	public void addProject(View v) {
		Intent myIntent = new Intent(this, editproject.class);
		Bundle dataBundle = new Bundle();
		dataBundle.putInt("isAdd", 1);
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 1);
	}
	
	private void save(Intent data) {
		Bundle dataBundle = data.getExtras();
		String projectName = dataBundle.getString("projectName");
		String description = dataBundle.getString("description");
		int dueMonth = dataBundle.getInt("dueMonth");
		int dueDay = dataBundle.getInt("dueDay");
		int dueYear = dataBundle.getInt("dueYear");
		String deadline = String.valueOf(dueMonth) +"/"+String.valueOf(dueDay)+"/"+String.valueOf(dueYear);
		
		Calendar cal = Calendar.getInstance();
		int todayMonth = cal.get(Calendar.MONTH);
		int todayDay = cal.get(Calendar.DAY_OF_MONTH);
		int todayYear = cal.get(Calendar.YEAR);
		String todayDate = String.valueOf(todayMonth) +"/"+String.valueOf(todayDay)+"/"+String.valueOf(todayYear);

		PROJECT project = datasource.createProject(projectName, todayDate, "0", deadline, description, "");
		adapter.add(project);
	}
	
	private void delete(Intent data) {
		Bundle dataBundle = data.getExtras();
		int place = dataBundle.getInt("place");
		PROJECT project = projectNames.get(place);
		datasource.deleteProject(project);
		adapter.remove(project);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK)
		{	
			if(requestCode == 1) {
				Bundle dataBundle = data.getExtras();
				int fromSave = dataBundle.getInt("fromSave", 0);
				if (fromSave == 1) { //coming from save
					if (dataBundle.getInt("isNotEdit") == 1)
						save(data);
					else {
						//maybe make an update method for this...
						delete(data);
						save(data);
					}
				}
				else {
					delete(data);
				}
			}
			else if (requestCode == 2) {
				delete(data);
			}
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//Toast.makeText(this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
		List<String> row = datasource.getRow(projectNames.get(arg2).toString());
			
//		Toast.makeText(getApplicationContext(), "row is " 
//			    + row.get(0)
//			    + row.get(4)
//			    + row.get(3), Toast.LENGTH_SHORT).show();
		
		Intent myIntent = new Intent(this, editproject.class);
		Bundle dataBundle = new Bundle();
		dataBundle.putInt("isAdd", 0);
		dataBundle.putString("projectName", row.get(0));
		dataBundle.putString("description", row.get(4));
		dataBundle.putString("dueDate", row.get(3));
		dataBundle.putInt("place", arg2);
		
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 1);
		
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//List<String> row = datasource.getRow(projectNames.get(arg2).toString());
		
//		Toast.makeText(getApplicationContext(), "row is " 
//	    + row.get(0)
//	    + row.get(4)
//	    + row.get(3), Toast.LENGTH_SHORT).show();
		
		Intent myIntent = new Intent(this, TabsFragmentActivity.class);
		Bundle dataBundle = new Bundle();
		//dataBundle.putInt("isAdd", 1);
		dataBundle.putString("pName", projectNames.get(arg2).toString());
		dataBundle.putInt("place", arg2);
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 2);
		//set as activity for result only get result when delete
	}
}
