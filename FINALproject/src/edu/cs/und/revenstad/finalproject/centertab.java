package edu.cs.und.revenstad.finalproject;

import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class centertab extends Fragment {
	TextView txtProjectNameData;
	TextView txtDescriptionData;
	TextView txtStartDateData;
	TextView txtDueDateData;
	TextView txtTimeData;
	TextView txtNumberTaskData;
	List<String> row;
	private projectDataSource datasource;
	String projectName;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.centertab, container, false);
        txtProjectNameData = (TextView)v.findViewById(R.id.txtProjectNameData);
        txtDescriptionData = (TextView)v.findViewById(R.id.txtDescriptionData);
        txtStartDateData = (TextView)v.findViewById(R.id.txtStartDateData);
        txtDueDateData = (TextView)v.findViewById(R.id.txtDueDateData);
        txtTimeData = (TextView)v.findViewById(R.id.txtTimeData);
        txtNumberTaskData = (TextView)v.findViewById(R.id.txtNumberTaskData);
        
        ActionBar ab = getActivity().getActionBar();
        projectName = ab.getTitle().toString();
        
        datasource = new projectDataSource(getActivity());
	    datasource.open();
	    
        row = datasource.getRow(projectName);
        
        txtProjectNameData.setText(row.get(0));
        txtDescriptionData.setText(row.get(4));
        txtStartDateData.setText(row.get(1));
        txtDueDateData.setText(row.get(3));
        int milsec = Integer.parseInt(row.get(2));
        int sec = milsec/1000;
        txtTimeData.setText(String.valueOf(sec));
        String aTasks = row.get(5);
        String allTasks[] = aTasks.split(",");
        txtNumberTaskData.setText(String.valueOf(allTasks.length/2));
        
        return v;
    }
     
}