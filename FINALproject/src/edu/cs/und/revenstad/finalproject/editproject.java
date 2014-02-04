package edu.cs.und.revenstad.finalproject;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

public class editproject extends Activity implements OnDateChangedListener {
	Button btnSave, btnDelete, btnCancel;
	EditText edtProjectName, edtDescription;
	DatePicker datePicker;
	
	int isAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editproject);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnDelete = (Button)findViewById(R.id.btnDelete);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		edtProjectName = (EditText)findViewById(R.id.edtProjectName);
		edtDescription = (EditText)findViewById(R.id.edtDescription);
		datePicker = (DatePicker)findViewById(R.id.datePicker1);
		
		Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePicker.init(mYear, mMonth, mDay, this);
		
		addOrEdit();
	}
	
	public void addOrEdit() {
		Intent localIntent = getIntent();
		Bundle dataBundle = localIntent.getExtras();
		isAdd = dataBundle.getInt("isAdd");
		if (isAdd == 1) {
			btnDelete.setVisibility(View.GONE);
		}
		else {
			btnDelete.setVisibility(View.VISIBLE);
			String date = dataBundle.getString("dueDate");
			edtProjectName.setText(dataBundle.getString("projectName"));
			edtDescription.setText(dataBundle.getString("description"));
			String[] newDate = date.split("/");
			datePicker.updateDate(Integer.parseInt(newDate[2]), Integer.parseInt(newDate[0]), Integer.parseInt(newDate[1]));
		}
	}
	
	public void saveData(View v) {
		//should check to see if all text fields are filled
		Intent localIntent = getIntent();
		Bundle dataBundle = localIntent.getExtras();
		dataBundle.putInt("fromSave", 1);
		dataBundle.putInt("isNotEdit", isAdd);
		dataBundle.putString("projectName", edtProjectName.getText().toString());
		dataBundle.putString("description", edtDescription.getText().toString());
		dataBundle.putInt("dueMonth", datePicker.getMonth());
		dataBundle.putInt("dueDay", datePicker.getDayOfMonth());
		dataBundle.putInt("dueYear", datePicker.getYear());
		
		localIntent.putExtras(dataBundle);
		setResult(Activity.RESULT_OK, localIntent);
		this.finish();
	}
	
	public void deleteProject(View v) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		builder = new AlertDialog.Builder(this);
		builder.setTitle("Are you sure you want to delete this project?");
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent localIntent = getIntent();
				Bundle dataBundle = localIntent.getExtras();
				int place = dataBundle.getInt("place");
				dataBundle.putInt("fromSave", 0);
				dataBundle.putInt("place", place);
				
				localIntent.putExtras(dataBundle);
				setResult(Activity.RESULT_OK, localIntent);
				stop();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing
			}
		});
		builder.setCancelable(false);
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	public void stop() {
		this.finish();
	}
	
	public void btnCancel(View v) {
		this.finish();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
}
