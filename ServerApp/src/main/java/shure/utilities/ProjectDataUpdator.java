package shure.utilities;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import shure.model.BugDataEntry;
import shure.model.Project;
import shure.model.ProjectStatus;
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
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	private boolean eventPublished;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 2, initialDelay = 1000 * 60)
	public void onSchedule() throws MalformedURLException, IOException {
		updateProjects();
		if (!eventPublished) {
			eventPublisher.publishEvent(new FillMissingProjectDataEvent());
			eventPublished = true;
		}
	}
	
	private void updateProjects() throws MalformedURLException, IOException {
		System.out.println("Running project data updates:");
		for (Project project : repositoryProjects.findAll()) {
			if (project.getFftStatus() == ProjectStatus.ON_HOLD) {
				System.out.println(project.getName() + " is on hold! Skipping update...");
				continue;
			}
			System.out.println("Fetching data for " + project.getName() + "...");
			NittanyUrlReader reader = new NittanyUrlReader(project.getNittanyUrl());
			if (reader.readUrl()) {
				updateTestDataEntries(project, reader.getJson());
				updateBugDataEntries(project, reader.getJson());
				repositoryProjects.save(project);
			}

		}
	
	}

	private void updateTestDataEntries(Project project, JSONObject nittanyData) {
		JSONArray lineChartResults = nittanyData.getJSONArray("line_chart_results");
		if (!lineChartResults.isEmpty()) {
			JSONObject lineChartResult = lineChartResults.getJSONObject(lineChartResults.length() - 1);
			TestDataEntry newTestDataEntry = new TestDataEntry(project.getName(), lineChartResult);
			if (project.addDataEntry(newTestDataEntry)) {
				repositoryTestDataEntries.save(newTestDataEntry);
			}

		}

	}

	private void updateBugDataEntries(Project project, JSONObject nittanyData) {
		JSONArray allBugs = nittanyData.getJSONArray("all_bugs");
		if (!allBugs.isEmpty()) {
			BugDataEntry newBugDataEntry = new BugDataEntry(project.getName(), allBugs, project.getComponentsList());
			if (project.addDataEntry(newBugDataEntry)) {
				repositoryBugDataEntries.save(newBugDataEntry);
			}

		}

	}

}
