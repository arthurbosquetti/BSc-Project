package shure.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import shure.utilities.NittanyUrlReader;

@Entity
public class Project {

	@Id
	@Column
	private String name;
	@Column
	private String nittanyUrl;
	@Column
	private String fftDeadline;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> componentsList;
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TestDataEntry> testDataEntries;
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<BugDataEntry> bugDataEntries;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNittanyUrl() {
		return nittanyUrl;
	}
	
	public void setNittanyUrl(String nittanyUrl) {
		try {
			NittanyUrlReader reader = new NittanyUrlReader(nittanyUrl);
			if (reader.readUrl()) {
				this.nittanyUrl = nittanyUrl;
			}
		} catch (IOException e) {
		}
	}

	public String getFftDeadline() {
		return fftDeadline;
	}

	public void setFftDeadline(String fftDeadline) {
		this.fftDeadline = fftDeadline;
	}

	public List<String> getComponentsList() {
		return componentsList;
	}
	
	public void setComponentsList(List<String> componentsList) {
		for (String component : componentsList) {
			this.componentsList.add(component.toLowerCase());
		}
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

	public boolean addDataEntry(TestDataEntry testDataEntry) {
		if (!testDataEntries.contains(testDataEntry)) {			
			testDataEntries.add(testDataEntry);
			sortTestDataEntries();
			return true;
		}
		return false;
	}
	
	public boolean addDataEntry(BugDataEntry bugDataEntry) {
		if (!bugDataEntries.contains(bugDataEntry)) {
			bugDataEntries.add(bugDataEntry);
			sortBugDataEntries();
			return true;
		}
		return false;
	}
	
	private void sortTestDataEntries() {
		Collections.sort(testDataEntries, new Comparator<TestDataEntry>() {
		    public int compare(TestDataEntry e1, TestDataEntry e2) {
		        return e1.getDataEntryId().getEntryDate().compareTo(e2.getDataEntryId().getEntryDate());
		    }
		});
	}
	
	private void sortBugDataEntries() {
		Collections.sort(bugDataEntries, new Comparator<BugDataEntry>() {
		    public int compare(BugDataEntry e1, BugDataEntry e2) {
		        return e1.getDataEntryId().getEntryDate().compareTo(e2.getDataEntryId().getEntryDate());
		    }
		});
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
