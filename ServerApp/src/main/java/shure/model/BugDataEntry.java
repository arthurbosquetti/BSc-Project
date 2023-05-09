package shure.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class BugDataEntry extends DataEntry {

	@Column
	private int openBlockerBugs;
	@Column
	private int openCriticalBugs;
	@Column
	private int openMajorBugs;
	@Column
	private int openMinorBugs;
	@Column
	private int openTrivialBugs;
	@Column
	private int fixedBlockerBugs;
	@Column
	private int fixedCriticalBugs;
	@Column
	private int fixedMajorBugs;
	@Column
	private int fixedMinorBugs;
	@Column
	private int fixedTrivialBugs;

	public BugDataEntry() {
	}

	public BugDataEntry(DataEntryId dataEntryId) {
		super(dataEntryId);
	}

	public BugDataEntry(String projectName, JSONArray allBugs, List<String> projectComponentsList) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String entryDate = localDate.getYear() + "-" + String.format("%02d", localDate.getMonthValue()) + "-"
				+ String.format("%02d", localDate.getDayOfMonth());

		setDataEntryId(new DataEntryId(entryDate, projectName));

		for (int i = 0; i < allBugs.length(); i++) {
			JSONObject entryJsonObject = allBugs.getJSONObject(i);

			JSONArray entryComponentsList = entryJsonObject.getJSONArray("componentsList");
			boolean entryHasRelevantComponent = false;
			for (int j = 0; j < entryComponentsList.length(); j++) {
				if (projectComponentsList.contains(entryComponentsList.getString(j).toLowerCase())
						|| projectComponentsList.contains("all")) {
					entryHasRelevantComponent = true;
					break;
				}
			}
			if (!entryHasRelevantComponent)
				continue;

			String severity = entryJsonObject.getString("severity");
			String status = entryJsonObject.getString("status");

			if ("5-Blocker".equals(severity) && "Fixed".equals(status))
				this.fixedBlockerBugs++;
			else if ("5-Blocker".equals(severity) && !"Closed".equals(status))
				this.openBlockerBugs++;
			else if ("4-Critical".equals(severity) && "Fixed".equals(status))
				this.fixedCriticalBugs++;
			else if ("4-Critical".equals(severity) && !"Closed".equals(status))
				this.openCriticalBugs++;
			else if ("3-Major".equals(severity) && "Fixed".equals(status))
				this.fixedMajorBugs++;
			else if ("3-Major".equals(severity) && !"Closed".equals(status))
				this.openMajorBugs++;
			else if ("2-Minor".equals(severity) && "Fixed".equals(status))
				this.fixedMinorBugs++;
			else if ("2-Minor".equals(severity) && !"Closed".equals(status))
				this.openMinorBugs++;
			else if ("1-Trivial".equals(severity) && "Fixed".equals(status))
				this.fixedTrivialBugs++;
			else if ("1-Trivial".equals(severity) && !"Closed".equals(status))
				this.openTrivialBugs++;
		}

	}

	public int getOpenBlockerBugs() {
		return openBlockerBugs;
	}

	public void setOpenBlockerBugs(int openBlockerBugs) {
		this.openBlockerBugs = openBlockerBugs;
	}

	public int getOpenCriticalBugs() {
		return openCriticalBugs;
	}

	public void setOpenCriticalBugs(int openCriticalBugs) {
		this.openCriticalBugs = openCriticalBugs;
	}

	public int getOpenMajorBugs() {
		return openMajorBugs;
	}

	public void setOpenMajorBugs(int openMajorBugs) {
		this.openMajorBugs = openMajorBugs;
	}

	public int getOpenMinorBugs() {
		return openMinorBugs;
	}

	public void setOpenMinorBugs(int openMinorBugs) {
		this.openMinorBugs = openMinorBugs;
	}

	public int getOpenTrivialBugs() {
		return openTrivialBugs;
	}

	public void setOpenTrivialBugs(int openTrivialBugs) {
		this.openTrivialBugs = openTrivialBugs;
	}

	public int getFixedBlockerBugs() {
		return fixedBlockerBugs;
	}

	public void setFixedBlockerBugs(int fixedBlockerBugs) {
		this.fixedBlockerBugs = fixedBlockerBugs;
	}

	public int getFixedCriticalBugs() {
		return fixedCriticalBugs;
	}

	public void setFixedCriticalBugs(int fixedCriticalBugs) {
		this.fixedCriticalBugs = fixedCriticalBugs;
	}

	public int getFixedMajorBugs() {
		return fixedMajorBugs;
	}

	public void setFixedMajorBugs(int fixedMajorBugs) {
		this.fixedMajorBugs = fixedMajorBugs;
	}

	public int getFixedMinorBugs() {
		return fixedMinorBugs;
	}

	public void setFixedMinorBugs(int fixedMinorBugs) {
		this.fixedMinorBugs = fixedMinorBugs;
	}

	public int getFixedTrivialBugs() {
		return fixedTrivialBugs;
	}

	public void setFixedTrivialBugs(int fixedTrivialBugs) {
		this.fixedTrivialBugs = fixedTrivialBugs;
	}

}
