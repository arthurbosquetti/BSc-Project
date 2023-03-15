package shure.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class DataEntryId implements Serializable {
	
	@Column
	private String entryDate;

	@Column
	private String projectName;
	
	protected DataEntryId() {}
	
	public DataEntryId(String entryDate, String projectName) {
		this.entryDate = entryDate;
		this.projectName = projectName;
	}
	
	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataEntryId) {
			return ((DataEntryId) obj).getEntryDate().equals(entryDate) && ((DataEntryId) obj).getProjectName().equals(getProjectName());
		}
		return false;
	}
	
	// @Override hash function
	
	@Override
	public String toString() {
		return "{" + projectName + ", " + entryDate + "}";
	}
		
}
