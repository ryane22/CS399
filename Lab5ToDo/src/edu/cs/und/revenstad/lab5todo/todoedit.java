package edu.cs.und.revenstad.lab5todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class todoedit extends Activity {
	Button btnDelete;
	EditText edtTask, edtDescription;
	DatePicker datePicker;
	
	int isAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todoedit);
		btnDelete = (Button)findViewById(R.id.btnDelete);
		edtTask = (EditText)findViewById(R.id.edtTask);
		edtDescription = (EditText)findViewById(R.id.edtDescription);
		datePicker = (DatePicker)findViewById(R.id.datePicker1);
		
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
			//have another button called to update data instead of save??
			String date = dataBundle.getString("dueDate");
			edtTask.setText(dataBundle.getString("taskName"));
			edtDescription.setText(dataBundle.getString("description"));
			String[] newDate = date.split("/");
			datePicker.updateDate(Integer.parseInt(newDate[2]), Integer.parseInt(newDate[0]), Integer.parseInt(newDate[1]));
		}
			
	}
	public void saveData(View v) {
		//check to see if all text fields are filled
		Intent localIntent = getIntent();
		Bundle dataBundle = localIntent.getExtras();
		dataBundle.putInt("fromSave", 1);
		dataBundle.putInt("isNotEdit", isAdd);
		dataBundle.putString("taskName", edtTask.getText().toString());
		dataBundle.putString("description", edtDescription.getText().toString());
		dataBundle.putInt("dueMonth", datePicker.getMonth());
		dataBundle.putInt("dueDay", datePicker.getDayOfMonth());
		dataBundle.putInt("dueYear", datePicker.getYear());
			
		localIntent.putExtras(dataBundle);
		setResult(Activity.RESULT_OK, localIntent);
		this.finish();
	}
	
	public void deleteTask(View v) {
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		builder = new AlertDialog.Builder(this);
		builder.setTitle("Are you sure you want to delete");
		
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
	
	public void btnCancel (View v) {
		this.finish();
	}
}
