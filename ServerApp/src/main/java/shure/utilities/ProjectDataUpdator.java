package shure.utilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import shure.model.BugDataEntry;
import shure.model.DataEntryId;
import shure.model.Project;
import shure.model.TestDataEntry;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.ProjectsRepository;
import shure.repositories.TestDataEntriesRepository;

@Component
public class ProjectDataUpdator {
	@Autowired
	private ProjectsRepository repositoryProjects;
	@Autowired
	private TestDataEntriesRepository repositoryTestDataEntries;
	@Autowired
	private BugDataEntriesRepository repositoryBugDataEntries;

	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void run() throws MalformedURLException, IOException {
		System.out.println("Running project data updates...");
		for (Project project : repositoryProjects.findAll()) {
			System.out.println("Fetching data for " + project.getName() + "...");
			JsonReader reader = new JsonReader(new URL(project.getNittanyUrl()));
			// TODO: if reading was unavailable, post a row with null values!
			reader.readUrl();
			updateDataEntries(project, new JSONObject(reader.getJsonString()));
			repositoryProjects.save(project);
		}

	}

	private Project updateDataEntries(Project project, JSONObject jsonObject) {
		System.out.println("creating new test data entry...");
		TestDataEntry newTestDataEntry = createNewTestDataEntry(project.getName(),
				jsonObject.getJSONArray("line_chart_results"));
		project.addDataEntry(newTestDataEntry);
		repositoryTestDataEntries.save(newTestDataEntry);

		System.out.println("creating new bug data entry...");
		BugDataEntry newBugDataEntry = createNewBugDataEntry(project.getName(), jsonObject.getString("release_name"),
				jsonObject.getJSONArray("all_bugs"));
		project.addDataEntry(newBugDataEntry);
		repositoryBugDataEntries.save(newBugDataEntry);

		return project;
	}

	private TestDataEntry createNewTestDataEntry(String projectName, JSONArray lineChartResults) {
		if (lineChartResults.isEmpty()) {
			// TODO: return something
			System.out.println("lineChartResults is Empty!");
		}

		JSONObject entryJsonObject = lineChartResults.getJSONObject(lineChartResults.length() - 1);
		String entryDate = entryJsonObject.getJSONObject("snapshotDateOnly").getString("date");
		TestDataEntry newTestDataEntry = new TestDataEntry(new DataEntryId(entryDate, projectName));

		newTestDataEntry.setProductName("TBD");
		newTestDataEntry.setTotalTests(entryJsonObject.getInt("total"));
		newTestDataEntry.setTestsPassed(entryJsonObject.getInt("passed"));
		newTestDataEntry.setTestsFailed(entryJsonObject.getInt("failed"));
		newTestDataEntry.setTestsBlocked(entryJsonObject.getInt("blocked"));
		newTestDataEntry.setTestsNotCompleted(entryJsonObject.getInt("notCompleted"));
		newTestDataEntry.setTestsNoRun(
				newTestDataEntry.getTotalTests() - newTestDataEntry.getTestsPassed() - newTestDataEntry.getTestsFailed()
						- newTestDataEntry.getTestsBlocked() - newTestDataEntry.getTestsNotCompleted());
		newTestDataEntry.setTarget95((int) (0.95 * newTestDataEntry.getTotalTests()));
		int leftTo95Target = newTestDataEntry.getTarget95() - newTestDataEntry.getTestsPassed();
		if (leftTo95Target > 0)
			newTestDataEntry.setLeftToTarget(leftTo95Target);
		return newTestDataEntry;
	}

	private BugDataEntry createNewBugDataEntry(String projectName, String releaseName, JSONArray allBugs) {
		if (allBugs.isEmpty()) {
			// TODO: return something
			System.out.println("allBugs is Empty!");
		}
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String entryDate = localDate.getYear() + "-" + String.format("%02d", localDate.getMonthValue()) + "-"
				+ String.format("%02d", localDate.getDayOfMonth());

		BugDataEntry newBugDataEntry = new BugDataEntry(new DataEntryId(entryDate, projectName));
		newBugDataEntry.setSwVersion(releaseName);

		for (int i = 0; i < allBugs.length(); i++) {
			JSONObject entryJsonObject = allBugs.getJSONObject(i);
			String severity = entryJsonObject.getString("severity");
			String status = entryJsonObject.getString("status");

			if ("5-Blocker".equals(severity) && "Fixed".equals(status))
				newBugDataEntry.setFixedBlockerBugs(newBugDataEntry.getFixedBlockerBugs() + 1);
			else if ("5-Blocker".equals(severity) && !"Closed".equals(status))
				newBugDataEntry.setOpenBlockerBugs(newBugDataEntry.getOpenBlockerBugs() + 1);
			else if ("4-Critical".equals(severity) && "Fixed".equals(status))
				newBugDataEntry.setFixedCriticalBugs(newBugDataEntry.getFixedCriticalBugs() + 1);
			else if ("4-Critical".equals(severity) && !"Closed".equals(status))
				newBugDataEntry.setOpenCriticalBugs(newBugDataEntry.getOpenCriticalBugs() + 1);
			else if ("3-Major".equals(severity) && "Fixed".equals(status))
				newBugDataEntry.setFixedMajorBugs(newBugDataEntry.getFixedMajorBugs() + 1);
			else if ("3-Major".equals(severity) && !"Closed".equals(status))
				newBugDataEntry.setOpenMajorBugs(newBugDataEntry.getOpenMajorBugs() + 1);
			else if ("2-Minor".equals(severity) && "Fixed".equals(status))
				newBugDataEntry.setFixedMinorBugs(newBugDataEntry.getFixedMinorBugs() + 1);
			else if ("2-Minor".equals(severity) && !"Closed".equals(status))
				newBugDataEntry.setOpenMinorBugs(newBugDataEntry.getOpenMinorBugs() + 1);
			else if ("1-Trivial".equals(severity) && "Fixed".equals(status))
				newBugDataEntry.setFixedTrivialBugs(newBugDataEntry.getFixedTrivialBugs() + 1);
			else if ("1-Trivial".equals(severity) && !"Closed".equals(status))
				newBugDataEntry.setOpenTrivialBugs(newBugDataEntry.getOpenTrivialBugs() + 1);
		}
		return newBugDataEntry;
	}
}
