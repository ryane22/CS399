/**************************************************************/
/* implemented with help from this tutorial                   */
/* http://www.vogella.com/articles/AndroidSQLite/article.html */
/**************************************************************/

package edu.cs.und.revenstad.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_PROJECTS = "PROJECTS";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_START_DATE = "start_date";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_DEADLINE = "deadline";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TASKS = "tasks";
	public static final String COLUMN_STOP_TIME = "stop_time";
	public static final String COLUMN_RUNNING = "running";
	
	private static final String DATABASE_NAME = "sideproject_RE.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	    + TABLE_PROJECTS + "(" + COLUMN_ID
	    + " integer primary key autoincrement, " 
	    + COLUMN_NAME + " text not null, "
	    + COLUMN_START_DATE + " text not null, "
	    + COLUMN_TIME + " text not null, "
	    + COLUMN_DEADLINE + " text not null, "
	    + COLUMN_DESCRIPTION + " text not null, "
	    + COLUMN_TASKS + " text not null, "
	    + COLUMN_STOP_TIME + " text not null, "
	    + COLUMN_RUNNING + " text not null"
	    + ");";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
		        + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
		onCreate(db);
	}

}

