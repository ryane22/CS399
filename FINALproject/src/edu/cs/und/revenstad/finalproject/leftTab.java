package edu.cs.und.revenstad.finalproject;

import java.util.Calendar;
import java.util.List;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class leftTab extends Fragment implements OnClickListener {
    Chronometer chrono;
    TextView txt1;
    Button btnStart2, btnStop;
    int stoppedMilliseconds;
    private projectDataSource datasource;
    List<String> row;
    String projectName;
    Calendar c;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lefttab, container, false);
        //Toast.makeText(getActivity(), "in oncreateview", Toast.LENGTH_SHORT).show();
		chrono = (Chronometer)v.findViewById(R.id.chrono);
		txt1 = (TextView)v.findViewById(R.id.textView1);
		btnStart2 = (Button)v.findViewById(R.id.btnStart2);
		btnStop = (Button)v.findViewById(R.id.btnStop);
		btnStart2.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		
		//chrono.setFormat("H:MM:SS");
		c = Calendar.getInstance();
		
		ActionBar ab = getActivity().getActionBar();
        projectName = ab.getTitle().toString();
        
		datasource = new projectDataSource(getActivity());
	    datasource.open();
	    
	    row = datasource.getRow(projectName);
	    if (row.get(7).equals("f")) {
			int sTime = Integer.parseInt(row.get(2).toString());
			stoppedMilliseconds = sTime;
			chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
	    }
	    else {
	    	int sTime = Integer.parseInt(row.get(2).toString());
			stoppedMilliseconds = sTime;
			long diffTime = (long) c.getTimeInMillis() - Long.parseLong(row.get(6));
			stoppedMilliseconds += diffTime;
			chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
			chrono.start();
	    }
		
        return v;
    }

	public void setMill() {
		String chronoText = chrono.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
        	stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
        }
        else if (array.length == 3) {
        	stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000 
               + Integer.parseInt(array[1]) * 60 * 1000
               + Integer.parseInt(array[2]) * 1000;
        }
        
        chrono.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnStart2.getId()) {
			setMill();
			
	        chrono.start();
	        datasource.updateRunning(projectName, "t");
			//Toast.makeText(getActivity(), String.valueOf(SystemClock.elapsedRealtime() - stoppedMilliseconds) , Toast.LENGTH_LONG).show();
		}
		else if (v.getId() == btnStop.getId()) {
			setMill();
			//Toast.makeText(getActivity(), String.valueOf(stoppedMilliseconds), Toast.LENGTH_SHORT).show();
			datasource.updateTime(projectName, String.valueOf(stoppedMilliseconds));
			chrono.stop();
			datasource.updateRunning(projectName, "f");
		}
	}
	
	//need an onPause and onStop
	public void onPause() {
    	super.onPause();
    	long curTime = c.getTimeInMillis();
    	setMill();
    	datasource.updateStopTime(projectName, String.valueOf(curTime));
    	datasource.updateTime(projectName, String.valueOf(stoppedMilliseconds));
    	//Toast.makeText(getActivity(), String.valueOf(curTime), Toast.LENGTH_SHORT).show();
    }
}
