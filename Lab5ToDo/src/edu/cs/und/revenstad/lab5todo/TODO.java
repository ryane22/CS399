/**************************************************************/
/* implemented with help from this tutorial                   */
/* http://www.vogella.com/articles/AndroidSQLite/article.html */
/**************************************************************/

package edu.cs.und.revenstad.lab5todo;
 
public class TODO {
	private long id;
	private String todo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToDo() {
		return todo;
	}

	public void setToDo(String todo) {
		this.todo = todo;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return todo;
	}
}
