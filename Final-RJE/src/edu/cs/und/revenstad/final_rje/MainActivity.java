/*********************************/
/* Ryan Evenstad Final CSci 399  */
/* ryan.evenstad22@gmail.com     */
/*********************************/

package edu.cs.und.revenstad.final_rje;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	EditText edtFirst, edtLast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtFirst = (EditText)findViewById(R.id.edtFirst);
		edtLast = (EditText)findViewById(R.id.edtLast);
	}

	public void greet(View v) {
		if(edtFirst.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
		}
		else if(edtLast.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
		}
		else {
			String firstName = edtFirst.getText().toString();
			String lastName = edtLast.getText().toString();
			
			Intent myIntent = new Intent(this, greeting.class);
			Bundle dataBundle = new Bundle();
			dataBundle.putString("firstName", firstName);
			dataBundle.putString("lastName", lastName);
			myIntent.putExtras(dataBundle);
			startActivity(myIntent);
		}
	}
}
