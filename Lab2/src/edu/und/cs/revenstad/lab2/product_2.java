package edu.und.cs.revenstad.lab2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class product_2 extends Activity implements OnClickListener{
	Button btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_2);
		btnBack = (Button)findViewById(R.id.btnBack);
		btnBack.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnBack.getId())
		{
			this.finish();
		}
	}

}
