package edu.und.cs.revenstad.relativelayoutexample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity implements OnClickListener{
	
	Button btnOkay;
	Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnOkay = (Button) findViewById(R.id.btnOkay);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnOkay.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	
	public void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnOkay.getId())
		{
			EditText edtFirst = (EditText) findViewById(R.id.edtFirst);
			EditText edtLast = (EditText) findViewById(R.id.edtLast);
			EditText edtEntered = (EditText) findViewById(R.id.edtEntered);
			String output;
			RadioButton rdoFemale = (RadioButton) findViewById(R.id.rdoFemale);
			RadioButton rdoMale = (RadioButton) findViewById(R.id.rdoMale);
			
			output = "First Name: " + edtFirst.getText().toString() + "\n\n";
			output += "Last Name: " + edtLast.getText().toString() + "\n\n";
			if (rdoFemale.isChecked())
			{
				output += "Gender: Female\n\n";
				rdoFemale.setChecked(false);
			}
			else if (rdoMale.isChecked())
			{
				output += "Gender: Male\n\n";
				rdoMale.setChecked(false);
			}
			edtEntered.setText(output);
			hideKeyboard();
		}
		else if (v.getId() == btnCancel.getId())
		{
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
