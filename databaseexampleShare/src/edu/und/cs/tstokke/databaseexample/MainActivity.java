package edu.und.cs.tstokke.databaseexample;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener {

	TextView txtOutput;
	TextView edtNameToAdd;
	TextView edtPhoneToAdd;
	TextView edtNameToFind;
	Button btnAdd;
	Button btnSearch;
    SQLiteDatabase db;
	
    static final String LOGAPP = "DBExample";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtOutput = (TextView) findViewById (R.id.txtOutput);
		edtNameToAdd = (TextView) findViewById (R.id.edtNameToAdd);
		edtPhoneToAdd = (TextView) findViewById (R.id.edtPhoneToAdd);
		edtNameToFind = (TextView) findViewById (R.id.edtNameToFind);
		btnAdd = (Button) findViewById (R.id.btnAdd);
		btnSearch = (Button) findViewById (R.id.btnSearch);
		
		btnAdd.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnSearch.setOnLongClickListener(this);
		
		openDatabase();
	}
	
	public void openDatabase()
	{
		String sdCardPath = Environment.getExternalStorageDirectory().getPath();
		//String sdCardPath = "data/data/edu.und.cs.tstokke.databaseexample";
		String PACKAGE_NAME = getApplicationContext().getPackageName();
		String dbPath = sdCardPath + "/" + "names.db";
		Toast.makeText(this, "DB path " + dbPath, Toast.LENGTH_SHORT).show();
		
		try
		{
			SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); //doesn't work here
			createTable();
		}
		catch (SQLiteException e)
		{
			txtOutput.append("\n" + e.getMessage());
		}
	}

	public void createTable() {
		db.beginTransaction();
		try
		{
			db.setTransactionSuccessful();
			String sql = "create table tblNames ( recID integer PRIMARY KEY autoincrement, " +
												"name text, " +
												"phone text )";
			db.execSQL(sql);
			Log.i(LOGAPP, "database created");
		}
		catch (SQLException e)
		{
			Log.i(LOGAPP, "unable to create database");
		}
		db.endTransaction();
	}
	
	public void insertNewRecord() {
		if (!(edtNameToAdd.getText().toString().equals("") && edtPhoneToAdd.getText().toString().equals("")))
		{
			db.beginTransaction();
			try
			{
				String sql = "INSERT into tblNames (name, phone) values ('"+
								edtNameToAdd.getText().toString() + "', '" +
								edtPhoneToAdd.getText().toString() + "');";
				db.execSQL(sql);
				db.setTransactionSuccessful();
				Toast.makeText(this, "Added Information", Toast.LENGTH_SHORT).show();
				edtNameToAdd.setText("");
				edtPhoneToAdd.setText("");
			}
			catch (SQLException e)
			{
				
			}
			db.endTransaction();
		}
		else
		{
			Toast.makeText(this, "Missing information", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnAdd.getId())
		{
			insertNewRecord();
		}
		else if (v.getId() == btnSearch.getId())
		{
			showMatchingRecords();
		}
	}
	
	public void showMatchingRecords() {
		txtOutput.setText("\nMatching Records\n");
		String searchFor = edtNameToFind.getText().toString();
		Cursor c = db.rawQuery("SELECT * from tblNames where name LIKE '%" + searchFor + "%' order by name;", null); //LIKE is not case sensitive
		while (c.moveToNext())
		{
			String name = c.getString(c.getColumnIndex("name"));
			String phone = c.getString(c.getColumnIndex("phone"));
			txtOutput.append("\n" + name + " " + phone + "\n");
		}
		txtOutput.append("\nMatching Records: " + c.getCount());
	}
	
	public void showAllRecords() {
		txtOutput.setText("\nCurrent Records\n");
		Cursor c = db.rawQuery("SELECT * from tblNames;", null);
		while (c.moveToNext())
		{
			String name = c.getString(c.getColumnIndex("name"));
			String phone = c.getString(c.getColumnIndex("phone"));
			txtOutput.append("\n" + name + " " + phone + "\n");
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == btnSearch.getId())
		{
			showAllRecords();
		}
		return true;
	}
}