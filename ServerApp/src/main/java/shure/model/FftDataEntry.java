package shure.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class FftDataEntry extends DataEntry {
	@Column
	private int testsLeft;

	public FftDataEntry() {
	}

	public FftDataEntry(DataEntryId dataEntryId) {
		super(dataEntryId);
	}

	public FftDataEntry(String projectName, JSONArray testCaseResults, String projectReleaseName) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String entryDate = localDate.getYear() + "-" + String.format("%02d", localDate.getMonthValue()) + "-"
				+ String.format("%02d", localDate.getDayOfMonth());

		setDataEntryId(new DataEntryId(entryDate, projectName));
		projectReleaseName = projectReleaseName.toUpperCase().replace(".X", "");

		for (int i = 0; i < testCaseResults.length(); i++) {
			JSONObject entryJsonObject = testCaseResults.getJSONObject(i);

			String currentState = entryJsonObject.getString("currentState");
			String lastVersion = entryJsonObject.getString("lastVersion");

			if (!lastVersion.startsWith(projectReleaseName) && ("No Run".equals(currentState) || "Not Completed".equals(currentState))) {
				testsLeft++;
			}
		}
	}

	public int getTestsLeft() {
		return testsLeft;
	}

	public void setTestsLeft(int testsLeft) {
		this.testsLeft = testsLeft;
	}
}
