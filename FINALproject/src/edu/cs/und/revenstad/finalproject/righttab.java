package edu.cs.und.revenstad.finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class righttab extends Fragment implements OnClickListener {
    ListView lstTasks;
    Button btnAddTask;
    private projectDataSource datasource;
    ArrayAdapter<String> adapter;
    List<String> row;
    String uTask, projectName;
    List<String> taskList;
    ArrayList<String> alTasks = new ArrayList<String>();
    ArrayList<String> alExtra = new ArrayList<String>();
    
    ArrayList <HashMap<String, String>> map;
    SimpleAdapter adapterTwoLineItem;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.righttab, container, false);
        lstTasks = (ListView)v.findViewById(R.id.lstTasks);
        btnAddTask = (Button)v.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(this);
        
        lstTasks.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				longItemClick(arg1, arg2);
				//((TextView)arg1).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				return false;
			}
        });
        
        ActionBar ab = getActivity().getActionBar();
        projectName = ab.getTitle().toString();
        //Toast.makeText(getActivity(), projectName,Toast.LENGTH_SHORT).show();
        
        datasource = new projectDataSource(getActivity());
	    datasource.open();
	    
	    row = datasource.getRow(projectName);
	    uTask = row.get(5);
	    //Toast.makeText(getActivity(), uTask,Toast.LENGTH_SHORT).show();
	    
	    String allTasks[] = uTask.split(",");
	    alTasks.clear();
	    alExtra.clear();
	    if (allTasks.length > 1) {
		    for (int i=0; i<allTasks.length; i+=2) {
		    	alTasks.add(allTasks[i]);
		    	alExtra.add(allTasks[i+1]);
		    }
	    }
	    
	    map = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < allTasks.length/2; ++i)
		{
			HashMap<String, String> newItem = new HashMap<String, String>();
			newItem.put("line1", alTasks.get(i));
			newItem.put("line2", alExtra.get(i));
			map.add(newItem);
		}
	    //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,  alTasks);
		adapterTwoLineItem = new SimpleAdapter(getActivity(), map, android.R.layout.simple_list_item_2,
				new String[]{"line1", "line2"},
				new int[]{android.R.id.text1, android.R.id.text2});
		lstTasks.setAdapter(adapterTwoLineItem);
		
		//lstTasks.setAdapter(adapter);
        
        return v;
    }

	@Override
	public void onClick(View v) {
		Intent myIntent = new Intent(getActivity(), addtask.class);
		Bundle dataBundle = new Bundle();
		myIntent.putExtras(dataBundle);
		startActivityForResult(myIntent, 1);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK)
		{	
			Bundle dataBundle = data.getExtras();
			String newTask = dataBundle.getString("task");
			
			if (uTask.equals(""))
				uTask = newTask + ",Incomplete";
			else
				uTask = uTask+","+newTask+",Incomplete";
			
			alTasks.add(newTask);
	    	alExtra.add("Incomplete");
			datasource.updateTask(projectName, uTask);
			
			HashMap<String, String> newItem = new HashMap<String, String>();
			newItem.put("line1", newTask);
			newItem.put("line2", "Incomplete");
			map.add(newItem);
			adapterTwoLineItem.notifyDataSetChanged();
			
			//adapter.notifyDataSetChanged();
		}
	}
	
	public void longItemClick(View arg1, int arg2) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Have You Completed This Task?");
		final int place = arg2;
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alExtra.set(place, "Complete");
				String tempTask = "";
				for (int i=0; i<alExtra.size(); i++) {
					if (i==0)
						tempTask = tempTask + alTasks.get(i) + "," + alExtra.get(i);
					else
						tempTask = tempTask + "," + alTasks.get(i) + "," + alExtra.get(i);
				}
				datasource.updateTask(projectName, tempTask);
				map.clear();
				for (int i = 0; i < alExtra.size(); ++i)
				{
					HashMap<String, String> newItem = new HashMap<String, String>();
					newItem.put("line1", alTasks.get(i));
					newItem.put("line2", alExtra.get(i));
					map.add(newItem);
				}
				adapterTwoLineItem.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alExtra.set(place, "Incomplete");
				String tempTask = "";
				for (int i=0; i<alExtra.size(); i++) {
					if (i==0)
						tempTask = tempTask + alTasks.get(i) + "," + alExtra.get(i);
					else
						tempTask = tempTask + "," + alTasks.get(i) + "," + alExtra.get(i);
				}
				datasource.updateTask(projectName, tempTask);
				map.clear();
				for (int i = 0; i < alExtra.size(); ++i)
				{
					HashMap<String, String> newItem = new HashMap<String, String>();
					newItem.put("line1", alTasks.get(i));
					newItem.put("line2", alExtra.get(i));
					map.add(newItem);
				}
				adapterTwoLineItem.notifyDataSetChanged();
			}
		});
		builder.setCancelable(false);
		alertDialog = builder.create();
		alertDialog.show();
	}
}
