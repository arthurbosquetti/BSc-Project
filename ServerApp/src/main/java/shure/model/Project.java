package shure.model;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import misc.JsonReader;


@Entity
public class Project {
	
	@Id
	@Column
	private String name;
	@Column
	private String nittanyUrl;
	@Column
	private String fftDeadline;

	@OneToMany
	private List<TestDataEntry> testDataEntries;
	@OneToMany
	private List<BugDataEntry> bugDataEntries;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		public void setNittanyUrl(String nittanyUrl) {
		JsonReader reader;
		try {
			reader = new JsonReader(new URL(nittanyUrl));
			if (reader.readUrl()) {			
				this.nittanyUrl = nittanyUrl;
			}
		} catch (IOException e) {}
	}
		
	public void addDataEntry(TestDataEntry testDataEntry) {
		testDataEntries.add(testDataEntry);
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
	
	public String getNittanyUrl() {
		return nittanyUrl;
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










