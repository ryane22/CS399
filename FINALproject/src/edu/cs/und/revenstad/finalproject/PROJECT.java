package edu.cs.und.revenstad.finalproject;

public class PROJECT {
	private long id;
	private String project;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return project;
	}
}
