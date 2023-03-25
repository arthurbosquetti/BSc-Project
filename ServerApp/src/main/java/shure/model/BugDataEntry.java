package shure.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BugDataEntry extends DataEntry {

	@Column
	private String swVersion;
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

	public String getSwVersion() {
		return swVersion;
	}

	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
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
