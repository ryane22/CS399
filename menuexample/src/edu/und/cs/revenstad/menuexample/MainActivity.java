package edu.und.cs.revenstad.menuexample;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnFocusChangeListener {
	
	boolean input1Active, input2Active;
	
	EditText edtInput1;
	EditText edtInput2;
	//adding 0 so that menu id's match array indexes
	Integer[] pointSizes = {0, 12, 16, 20, 24, 28};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtInput1 = (EditText) findViewById(R.id.edtInput1);
		edtInput1 = (EditText) findViewById(R.id.edtInput2);
		
		//tell the system these widgets should have a context menu
		registerForContextMenu(edtInput1);
		registerForContextMenu(edtInput2);
		
		edtInput1.setOnFocusChangeListener(this);
		edtInput2.setOnFocusChangeListener(this);
	}
	
	private void createFirstMenu(Menu menu){
		int groupId = 0;
		//parameters to add are: groupId, itemId, order, title
		menu.add(groupId, 1, 1, "12 point");
		menu.add(groupId, 2, 2, "16 point");
		menu.add(groupId, 3, 3, "20 point");
		menu.add(groupId, 4, 4, "24 point");
		menu.add(groupId, 5, 5, "28 point");
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		Toast.makeText(getApplicationContext(), getResources().getResourceEntryName(v.getId()), 1).show();
		if (hasFocus)
		{
			if (v.getId() == edtInput1.getId())
			{
				input1Active = true;
				input2Active = false;
			}
			else if (v.getId() == edtInput2.getId())
			{
				input1Active = false;
				input2Active = true;
			}
		}
	}
	
	private boolean handleMenuSelection(MenuItem item){
		int menuItemId = item.getItemId();
		if (menuItemId <= 5)
		{
			if (input1Active)
				edtInput1.setTextSize(pointSizes[menuItemId]);
			else if (input2Active)
				edtInput2.setTextSize(pointSizes[menuItemId]);
		}
		else
		{
			String newText = edtInput2.getText().toString();
			Spanned result = null;
			if (menuItemId == 11)
			{
				result = Html.fromHtml("<normal>" + newText + "</normal>");
				edtInput2.setText(result);
			}
			else if (menuItemId == 12)
			{
				result = Html.fromHtml("<B>" + newText + "</B>");
				edtInput2.setText(result);
			}
			else if (menuItemId == 13)	
				//result = Html.fromHtml("<I>" + newText + "</I>");
				//edtInput2.setText(result);
				edtInput2.setTypeface(null, Typeface.ITALIC);
		}
		
		return false;
	}
		
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return handleMenuSelection(item) || super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return (handleMenuSelection(item) || super.onOptionsItemSelected(item));
	}

	private void createSecondMenu(ContextMenu menu){
		int groupId = 0;
		menu.setHeaderTitle("Font Style");
		menu.add(groupId, 11, 1, "Normal");
		menu.add(groupId, 12, 2, "Bold");
		menu.add(groupId, 13, 3, "Italic");
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == edtInput1.getId())
		{
			createFirstMenu(menu); //work to create menu could be done here
		}
		else if (v.getId() == edtInput2.getId())
		{
			createSecondMenu(menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		createFirstMenu(menu);
		return true;
	}

}
