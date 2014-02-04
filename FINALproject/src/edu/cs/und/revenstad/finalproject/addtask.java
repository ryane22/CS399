package edu.cs.und.revenstad.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class addtask extends Activity {
	EditText edtTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtask);
		edtTask = (EditText)findViewById(R.id.edtTask);
	}
	
	public void saveTask(View v) {
		String task = edtTask.getText().toString();
		if(task.equals("")) {
			Toast.makeText(this, "No Task Entered", Toast.LENGTH_SHORT).show();
		}
		else {
			Intent localIntent = getIntent();
			Bundle dataBundle = localIntent.getExtras();
			dataBundle.putString("task", task);
			
			localIntent.putExtras(dataBundle);
			setResult(Activity.RESULT_OK, localIntent);
			this.finish();
		}
	}
	
	public void cancelTask(View v) {
		finish();
	}
	
}
