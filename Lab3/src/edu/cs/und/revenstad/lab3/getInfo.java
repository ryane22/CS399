package edu.cs.und.revenstad.lab3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class getInfo extends Activity{
	Button btnSave;
	Button btnClear;
	Button btnExit;
	EditText edtName;
	EditText edtNumber;
	CheckBox chkShowNumber;
	
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor myEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getinfo);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnClear = (Button)findViewById(R.id.btnClear);
		btnExit = (Button)findViewById(R.id.btnExit);
		edtName = (EditText)findViewById(R.id.edtName);
		edtNumber = (EditText)findViewById(R.id.edtNumber);
		chkShowNumber = (CheckBox)findViewById(R.id.chkShowNumber);
	}
	
	public void save(View v){
		if(edtName.getText().toString().equals("") || edtNumber.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
		}
		else
		{
			mySharedPreferences = getSharedPreferences("MySettings", Activity.MODE_PRIVATE);
			myEditor = mySharedPreferences.edit();
			
			//get the original intent sent to this activity
			Intent localIntent = getIntent();
			//grab the data we need with the various get methods
			Bundle dataBundle = localIntent.getExtras();
			int contactNum = dataBundle.getInt("contactNum");
			
			String contactNameNum = "name"+contactNum;
			myEditor.putString(contactNameNum, edtName.getText().toString());
			String contactNumNum = "number"+contactNum;
			myEditor.putString(contactNumNum, edtNumber.getText().toString());
			if (chkShowNumber.isChecked())
			{
				String isCheckedNum = "check"+contactNum;
				myEditor.putInt(isCheckedNum, 1);
			}
			else
			{
				String isCheckedNum = "check"+contactNum;
				myEditor.putInt(isCheckedNum, 0);
			}
			
			myEditor.commit();
			dataBundle.putInt("result", contactNum);
			localIntent.putExtras(dataBundle);
			setResult(Activity.RESULT_OK, localIntent);
			this.finish();
		}
	}
	
	//The assignment is unclear as to whether this should clear the name and number fields
	//or remove the data associated with this contact from the preference file
	public void clear(View v){
		edtName.setText("");
		edtNumber.setText("");
	}
	
	public void exit(View v){
		this.finish();
	}
}
