package shure.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	
	@Id
	@Column
	private String name;
	@Column
	private String fftDeadline;
	@OneToMany
//	@JsonIgnore
	private List<TestDataEntry> testDataEntries;
	@OneToMany
//	@JsonIgnore
	private List<BugDataEntry> bugDataEntries;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFftDeadline() {
		return fftDeadline;
	}
	
	public void setFftDeadline(String fftDeadline) {
		this.fftDeadline = fftDeadline;
	}
	
	public List<TestDataEntry> getTestDataEntries() {
		return testDataEntries;
	}
	
	public void setTestDataEntries(ArrayList<TestDataEntry> testDataEntries) {
		this.testDataEntries = testDataEntries;
	}
	
	public List<BugDataEntry> getBugDataEntries() {
		return bugDataEntries;
	}
	
	public void setBugDataEntries(ArrayList<BugDataEntry> bugDataEntries) {
		this.bugDataEntries = bugDataEntries;
	}
	
	public void addDataEntry(TestDataEntry testDataEntry) {
		testDataEntries.add(testDataEntry);
	}
	
	public void addDataEntry(BugDataEntry bugDataEntry) {
		bugDataEntries.add(bugDataEntry);
	}
	
	public void removeDataEntry(TestDataEntry testDataEntry) {
		testDataEntries.remove(testDataEntry);
	}
	
	public void removeDataEntry(BugDataEntry bugDataEntry) {
		bugDataEntries.remove(bugDataEntry);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Project) {
			return name.equals(((Project) obj).getName());
		}
		return false;
	}
	// @Override Hash function

}










