package edu.und.cs.revenstad.lab2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	Button btnTie, btnBowTie, btnAscot, btnCalculate;
	EditText edtTieQuant, edtBowTieQuant, edtAscotQuant, edtTotal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnTie = (Button)findViewById(R.id.btnTie);
		btnBowTie = (Button)findViewById(R.id.btnBowTie);
		btnAscot = (Button)findViewById(R.id.btnAscot);
		btnCalculate = (Button)findViewById(R.id.btnCalculate);
		
		edtTieQuant = (EditText)findViewById(R.id.edtTieQuant);
		edtBowTieQuant = (EditText)findViewById(R.id.edtBowTieQuant);
		edtAscotQuant = (EditText)findViewById(R.id.edtAscotQuant);
		edtTotal = (EditText)findViewById(R.id.edtTotal);
		
		btnTie.setOnClickListener(this);
		btnBowTie.setOnClickListener(this);
		btnAscot.setOnClickListener(this);
		btnCalculate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btnTie.getId())
		{
			startActivity(new Intent(MainActivity.this, product_1.class));
		}
		else if (v.getId() == btnBowTie.getId())
		{
			startActivity(new Intent(MainActivity.this, product_2.class));
		}
		else if (v.getId() == btnAscot.getId())
		{
			startActivity(new Intent(MainActivity.this, product_3.class));
		}
		else if (v.getId() == btnCalculate.getId())
		{
			hideKeyboard();
			try
			{
				float a, b, c, d;
				a = Float.parseFloat(edtTieQuant.getText().toString());
				b = Float.parseFloat(edtBowTieQuant.getText().toString());
				c = Float.parseFloat(edtAscotQuant.getText().toString());
				d = (float) ((a*59.5)+(b*69.5)+(c*39.5));
				edtTotal.setText(Float.toString(d));
			}
			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(), "Invalid Quantity", Toast.LENGTH_SHORT).show();
				edtTieQuant.setText("0");
				edtBowTieQuant.setText("0");
				edtAscotQuant.setText("0");
				edtTotal.setText("");
			}
		}	
	}
	
	public void hideKeyboard()
	{
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
