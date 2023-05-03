package shure.utilities;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import shure.model.BugDataEntry;
import shure.model.DataEntryId;
import shure.model.FftDataEntry;
import shure.model.Project;
import shure.model.TestDataEntry;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.FftDataEntriesRepository;
import shure.repositories.ProjectsRepository;
import shure.repositories.TestDataEntriesRepository;

@Component
public class MissingProjectDataFiller {

	@Autowired
	private ProjectsRepository repositoryProjects;
	@Autowired
	private TestDataEntriesRepository repositoryTestDataEntries;
	@Autowired
	private FftDataEntriesRepository repositoryFftDataEntries;
	@Autowired
	private BugDataEntriesRepository repositoryBugDataEntries;

	@EventListener(FillMissingProjectDataEvent.class)
	public void onEvent() {
		fillMissingProjectData();
	}

	private void fillMissingProjectData() {
		for (Project project : repositoryProjects.findAll()) {
			System.out.println("Filling out empty data for " + project.getName() + "...");
			fillMissingTestDataEntries(project);
			fillMissingFftDataEntries(project);
			fillMissingBugDataEntries(project);
		}

	}

	private void fillMissingTestDataEntries(Project project) {
		if (project.getTestDataEntries().isEmpty()) {
			return;
		}
		
		String startDateString = project.getTestDataEntries().get(0).getDataEntryId().getEntryDate();
		String endDateString = project.getTestDataEntries().get(project.getTestDataEntries().size() - 1)
				.getDataEntryId().getEntryDate();

		LocalDate nextDate = LocalDate.parse(startDateString).plusDays(1);
		LocalDate endDate = LocalDate.parse(endDateString);

		while (nextDate.compareTo(endDate) < 0) {
			TestDataEntry missingTestDataEntry = new TestDataEntry(
					new DataEntryId(nextDate.toString(), project.getName()));
			if (project.addDataEntry(missingTestDataEntry)) {
				System.out.println(
						"Added missing test data entry with ID " + missingTestDataEntry.getDataEntryId().toString());
				repositoryTestDataEntries.save(missingTestDataEntry);
				repositoryProjects.save(project);
			}
			nextDate = nextDate.plusDays(1);
		}

	}
	
	private void fillMissingFftDataEntries(Project project) {
		if (project.getFftDataEntries().isEmpty()) {
			return;
		}
		
		String startDateString = project.getFftDataEntries().get(0).getDataEntryId().getEntryDate();
		String endDateString = project.getFftDataEntries().get(project.getFftDataEntries().size() - 1)
				.getDataEntryId().getEntryDate();

		LocalDate nextDate = LocalDate.parse(startDateString).plusDays(1);
		LocalDate endDate = LocalDate.parse(endDateString);

		while (nextDate.compareTo(endDate) < 0) {
			FftDataEntry missingFftDataEntry = new FftDataEntry(
					new DataEntryId(nextDate.toString(), project.getName()));
			if (project.addDataEntry(missingFftDataEntry)) {
				System.out.println(
						"Added missing FFT data entry with ID " + missingFftDataEntry.getDataEntryId().toString());
				repositoryFftDataEntries.save(missingFftDataEntry);
				repositoryProjects.save(project);
			}
			nextDate = nextDate.plusDays(1);
		}

	}

	private void fillMissingBugDataEntries(Project project) {
		if (project.getBugDataEntries().isEmpty()) {
			return;
		}
		
		String startDateString = project.getBugDataEntries().get(0).getDataEntryId().getEntryDate();
		String endDateString = project.getBugDataEntries().get(project.getBugDataEntries().size() - 1).getDataEntryId()
				.getEntryDate();

		LocalDate nextDate = LocalDate.parse(startDateString).plusDays(1);
		LocalDate endDate = LocalDate.parse(endDateString);

		while (nextDate.compareTo(endDate) < 0) {
			BugDataEntry missingBugDataEntry = new BugDataEntry(
					new DataEntryId(nextDate.toString(), project.getName()));
			if (project.addDataEntry(missingBugDataEntry)) {
				System.out.println(
						"Added missing bug data entry with ID " + missingBugDataEntry.getDataEntryId().toString());
				repositoryBugDataEntries.save(missingBugDataEntry);
				repositoryProjects.save(project);
			}
			nextDate = nextDate.plusDays(1);
		}

	}

}
