package edu.cs.und.revenstad.finalproject;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class projectDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper.COLUMN_ID,
			DBHelper.COLUMN_NAME,
			DBHelper.COLUMN_START_DATE,
			DBHelper.COLUMN_TIME,
			DBHelper.COLUMN_DEADLINE,
			DBHelper.COLUMN_DESCRIPTION,
			DBHelper.COLUMN_TASKS,
			DBHelper.COLUMN_STOP_TIME,
			DBHelper.COLUMN_RUNNING};

	public projectDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public PROJECT createProject(String name, String started, String time, String deadline, String description, String tasks) {
		ContentValues values = new ContentValues();
	    values.put(DBHelper.COLUMN_NAME, name);
	    values.put(DBHelper.COLUMN_START_DATE, started);
	    values.put(DBHelper.COLUMN_TIME, time);
	    values.put(DBHelper.COLUMN_DEADLINE, deadline);
	    values.put(DBHelper.COLUMN_DESCRIPTION, description);
	    values.put(DBHelper.COLUMN_TASKS, tasks);
	    values.put(DBHelper.COLUMN_STOP_TIME, "0");
	    values.put(DBHelper.COLUMN_RUNNING, "f");
	    long insertId = database.insert(DBHelper.TABLE_PROJECTS, null,
	        values);
	    Cursor cursor = database.query(DBHelper.TABLE_PROJECTS,
	        allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    PROJECT newToDo = cursorPROJECT(cursor);
	    cursor.close();
	    return newToDo;
	}

	public void deleteProject(PROJECT project) {
		long id = project.getId();
	    //System.out.println("Comment deleted with id: " + id);
	    database.delete(DBHelper.TABLE_PROJECTS, DBHelper.COLUMN_ID
	        + " = " + id, null);
	}

	public List<PROJECT> getAllProjects() {
	    List<PROJECT> comments = new ArrayList<PROJECT>();

	    Cursor cursor = database.query(DBHelper.TABLE_PROJECTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      PROJECT project = cursorPROJECT(cursor);
	      comments.add(project);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return comments;
	}

	private PROJECT cursorPROJECT(Cursor cursor) {
	    PROJECT project = new PROJECT();
	    project.setId(cursor.getLong(0));
	    project.setProject(cursor.getString(1));
	    return project;
	}
	
	public List<String> getRow(String name) {
		List<String> row = new ArrayList<String>();
		String searchFor = name;
		Cursor c = database.rawQuery("SELECT * from PROJECTS where name LIKE '" + searchFor + "';", null);
		while (c.moveToNext())
		{
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_NAME)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_START_DATE)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_TIME)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_DEADLINE)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASKS)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_STOP_TIME)));
			row.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_RUNNING)));
		}
		return row;
	}
	
	public void updateTask(String name, String tasks) {
		ContentValues cv = new ContentValues();
		cv.put("tasks", tasks);
		database.update("PROJECTS", cv, "name LIKE '"+name+"';", null);
	}
	
	public void updateTime(String name, String time) {
		ContentValues cv = new ContentValues();
		cv.put("time", time);
		database.update("PROJECTS", cv, "name LIKE '"+name+"';", null);
	}
	
	public void updateStopTime(String name, String stopTime) {
		ContentValues cv = new ContentValues();
		cv.put("stop_time", stopTime);
		database.update("PROJECTS", cv, "name LIKE '"+name+"';", null);
	}
	
	public void updateRunning(String name, String running) {
		ContentValues cv = new ContentValues();
		cv.put("running", running);
		database.update("PROJECTS", cv, "name LIKE '"+name+"';", null);
	}
}