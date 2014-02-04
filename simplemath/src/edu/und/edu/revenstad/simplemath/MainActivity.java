package edu.und.edu.revenstad.simplemath;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	EditText edtVal1, edtVal2;
	//EditText edtVal2;
	EditText edtResult;
	Button btnQuit;
	Button btnAdd;
	Button btnSub;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtVal1 = (EditText) findViewById (R.id.edtValue1);
		edtVal2 = (EditText) findViewById (R.id.edtValue2);
		edtResult = (EditText) findViewById (R.id.edtResult);
		
		btnQuit = (Button) findViewById(R.id.btnQuit);
		btnAdd = (Button) findViewById (R.id.btnAdd);
		btnSub = (Button) findViewById (R.id.btnMinus);
		
		btnAdd.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		btnQuit.setOnClickListener(this);
	}

	public void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == btnQuit.getId())
		{
			finish();
		}
		else
		{
			double edt1, edt2, result = 0;
			try {
				edt1 = Double.parseDouble(edtVal1.getText().toString());
				edt2 = Double.parseDouble(edtVal2.getText().toString());
				if (v.getId() == btnAdd.getId())
					result = edt1 + edt2;
				else if (v.getId() == btnSub.getId())
					result = edt1 - edt2;
				edtResult.setText(String.format("%.2f", result));
			}
			catch (Exception e) {
				Context context = getApplicationContext();
				CharSequence text = "Error";
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				edtResult.setText("");
			}
			hideKeyboard();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
