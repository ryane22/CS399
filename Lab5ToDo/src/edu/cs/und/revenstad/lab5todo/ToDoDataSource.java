/**************************************************************/
/* implemented with help from this tutorial                   */
/* http://www.vogella.com/articles/AndroidSQLite/article.html */
/**************************************************************/

package edu.cs.und.revenstad.lab5todo;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ToDoDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] allColumns = { DBHelper.COLUMN_ID,
			DBHelper.COLUMN_TASK,
			DBHelper.COLUMN_COMMENTS,
			DBHelper.COLUMN_DUE};

	public ToDoDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public TODO createTask(String task, String description, String dueDate) {
		ContentValues values = new ContentValues();
	    values.put(DBHelper.COLUMN_TASK, task);
	    values.put(DBHelper.COLUMN_COMMENTS, description);
	    values.put(DBHelper.COLUMN_DUE, dueDate);
	    long insertId = database.insert(DBHelper.TABLE_TODO, null,
	        values);
	    Cursor cursor = database.query(DBHelper.TABLE_TODO,
	        allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    TODO newToDo = cursorToTODO(cursor);
	    cursor.close();
	    return newToDo;
	}

	public void deleteTask(TODO task) {
		long id = task.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(DBHelper.TABLE_TODO, DBHelper.COLUMN_ID
	        + " = " + id, null);
	}

	public List<TODO> getAllComments() {
	    List<TODO> comments = new ArrayList<TODO>();

	    Cursor cursor = database.query(DBHelper.TABLE_TODO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      TODO comment = cursorToTODO(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return comments;
	}

	private TODO cursorToTODO(Cursor cursor) {
	    TODO comment = new TODO();
	    comment.setId(cursor.getLong(0));
	    comment.setToDo(cursor.getString(1));
	    return comment;
	}
	
	public List<String> getRow(String task) {
		List<String> row = new ArrayList<String>();
		String searchFor = task;
		Cursor c = database.rawQuery("SELECT * from TODO where task LIKE '" + searchFor + "';", null);
		while (c.moveToNext())
		{
			row.add(c.getString(c.getColumnIndex("task")));
			row.add(c.getString(c.getColumnIndex("comments")));
			row.add(c.getString(c.getColumnIndex("due")));
		}
		return row;
	}
}
