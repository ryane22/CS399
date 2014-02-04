package edu.cs.und.revenstad.test1app;

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

public class MainActivity extends Activity {
	EditText edtVal1, edtVal2, edtSum, edtMax, edtConcat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void button2 (View v) {
		hideKeyboard();
		
		EditText edtVal1 = (EditText)findViewById(R.id.edtVal1);
		EditText edtVal2 = (EditText)findViewById(R.id.edtVal2);
		EditText edtSum = (EditText)findViewById(R.id.edtSum);
		EditText edtMax = (EditText)findViewById(R.id.edtMax);
		EditText edtConcat = (EditText)findViewById(R.id.edtConcat);
		
		edtSum.setText("");
		edtSum.setText("");
		edtConcat.setText("");
		
		if (edtVal1.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Plese Enter Value 1", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int val1 = Integer.parseInt(edtVal1.getText().toString());
			if (edtVal2.getText().toString().equals(""))
			{
				Toast.makeText(getApplicationContext(), "Plese Enter Value 2", Toast.LENGTH_SHORT).show();
			}
			else
			{
				int val2 = Integer.parseInt(edtVal2.getText().toString());
				int sum = val1 + val2;
				edtSum.setText(String.valueOf(sum));
				if (val1 > val2)
				{
					edtMax.setText(String.valueOf(val1));
				}
				else
				{
					edtMax.setText(String.valueOf(val2));
				}
				edtConcat.setText(String.valueOf(val1)+String.valueOf(val2));
			}
		}
			
	}
	public void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
