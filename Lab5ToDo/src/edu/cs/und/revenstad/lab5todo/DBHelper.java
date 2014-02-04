/**************************************************************/
/* implemented with help from this tutorial                   */
/* http://www.vogella.com/articles/AndroidSQLite/article.html */
/**************************************************************/

package edu.cs.und.revenstad.lab5todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_TODO = "TODO";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TASK = "task";
	public static final String COLUMN_COMMENTS = "comments";
	public static final String COLUMN_DUE = "due";
	
	private static final String DATABASE_NAME = "lab5_todo_RE.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	    + TABLE_TODO + "(" + COLUMN_ID
	    + " integer primary key autoincrement, " 
	    + COLUMN_TASK + " text not null, "
	    + COLUMN_COMMENTS + " text not null, "
	    + COLUMN_DUE + " text not null"
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
		onCreate(db);
	}

}
