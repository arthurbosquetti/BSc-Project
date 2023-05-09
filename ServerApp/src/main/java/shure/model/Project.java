package shure.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
	@Column
	private String releaseName;
	@Column
	private boolean isActive = true;
	@Column
	private FftStatus fftStatus = FftStatus.UNDEFINED;
	@Column
	private boolean svApproved = false;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> componentsList = new ArrayList<>();
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TestDataEntry> testDataEntries;
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FftDataEntry> fftDataEntries;
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
		if (!(fftDeadline == null) && fftDeadline.isBlank()) {
			this.fftDeadline = null;
		} else {
			this.fftDeadline = fftDeadline;
		}
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setFftStatus(FftStatus fftStatus) {
		this.fftStatus = fftStatus;
	}

	public FftStatus getFftStatus() {
		return fftStatus;
	}

	public void updateFftStatus() {
		if (!isActive) {
			return;
		}

		if (fftDeadline == null || fftDataEntries.isEmpty()) {
			fftStatus = FftStatus.UNDEFINED;
			return;
		}

		FftDataEntry latestEntry = fftDataEntries.get(fftDataEntries.size() - 1);
		int testsLeft = latestEntry.getTestsLeft();
		if (testsLeft == 0) {
			fftStatus = FftStatus.COMPLETE;
			return;
		}

		LocalDate currentDate = LocalDate.parse(latestEntry.getDataEntryId().getEntryDate());
		LocalDate endDate = LocalDate.parse(fftDeadline);
		if (!currentDate.isBefore(endDate)) {
			fftStatus = FftStatus.INCOMPLETE;
			return;
		}

		LocalDate startDate = LocalDate.parse(fftDataEntries.get(0).getDataEntryId().getEntryDate());
		int testsLeftAtStart = fftDataEntries.get(0).getTestsLeft();
		float projectDuration = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		float idealRate = testsLeftAtStart / projectDuration;

		double expectedTestsLeft = Math.max(0, Math.floor(testsLeftAtStart - idealRate * fftDataEntries.size()));

		if (testsLeft > expectedTestsLeft * 1.5) {
			fftStatus = FftStatus.CRITICAL;
		} else if (testsLeft > expectedTestsLeft * 1.05) {
			fftStatus = FftStatus.BEHIND;
		} else if (testsLeft > expectedTestsLeft * 0.90) {
			fftStatus = FftStatus.ON_TRACK;
		} else {
			fftStatus = FftStatus.AHEAD;
		}
	}

	public boolean getSvApproved() {
		return svApproved;
	}

	public void setSvApproved(boolean svApproved) {
		this.svApproved = svApproved;
	}

	private void updateSvApproved() {
		if (bugDataEntries.isEmpty()) {
			svApproved = false;
			return;
		}

		BugDataEntry latestEntry = bugDataEntries.get(bugDataEntries.size() - 1);
		svApproved = latestEntry.getOpenBlockerBugs() == 0 && latestEntry.getFixedBlockerBugs() == 0
				&& latestEntry.getOpenCriticalBugs() == 0 && latestEntry.getFixedCriticalBugs() == 0
				&& (latestEntry.getOpenMajorBugs() + latestEntry.getFixedMajorBugs()) <= 10;

	}

	public List<String> getComponentsList() {
		return componentsList;
	}

	public void setComponentsList(List<String> componentsList) {
		this.componentsList.clear();
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

	public List<FftDataEntry> getFftDataEntries() {
		return fftDataEntries;
	}

	public void setFftDataEntries(ArrayList<FftDataEntry> fftDataEntries) {
		this.fftDataEntries = fftDataEntries;
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

	public boolean addDataEntry(FftDataEntry fftDataEntry) {
		if (fftDataEntries.isEmpty() && fftDataEntry.getTestsLeft() == 0) {
			return false;
		}
		if (!fftDataEntries.contains(fftDataEntry)) {
			fftDataEntries.add(fftDataEntry);
			sortFftDataEntries();
			updateFftStatus();
			return true;
		}
		return false;
	}

	public boolean addDataEntry(BugDataEntry bugDataEntry) {
		if (!bugDataEntries.contains(bugDataEntry)) {
			bugDataEntries.add(bugDataEntry);
			sortBugDataEntries();
			updateSvApproved();
			return true;
		}
		return false;
	}

	private void sortTestDataEntries() {
		Collections.sort(testDataEntries, new Comparator<TestDataEntry>() {
			@Override
			public int compare(TestDataEntry e1, TestDataEntry e2) {
				return e1.getDataEntryId().getEntryDate().compareTo(e2.getDataEntryId().getEntryDate());
			}
		});
	}

	private void sortFftDataEntries() {
		Collections.sort(fftDataEntries, new Comparator<FftDataEntry>() {
			@Override
			public int compare(FftDataEntry e1, FftDataEntry e2) {
				return e1.getDataEntryId().getEntryDate().compareTo(e2.getDataEntryId().getEntryDate());
			}
		});
	}

	private void sortBugDataEntries() {
		Collections.sort(bugDataEntries, new Comparator<BugDataEntry>() {
			@Override
			public int compare(BugDataEntry e1, BugDataEntry e2) {
				return e1.getDataEntryId().getEntryDate().compareTo(e2.getDataEntryId().getEntryDate());
			}
		});
	}

	public void removeDataEntry(TestDataEntry testDataEntry) {
		testDataEntries.remove(testDataEntry);
	}

	public void removeDataEntry(FftDataEntry fftDataEntry) {
		fftDataEntries.remove(fftDataEntry);
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

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
