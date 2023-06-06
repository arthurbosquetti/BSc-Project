package shure.utilities;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import shure.model.BugDataEntry;
import shure.model.DataEntryId;
import shure.model.FftDataEntry;
import shure.model.Project;
import shure.model.TestDataEntry;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.FftDataEntriesRepository;
import shure.repositories.ProjectsRepository;
import shure.repositories.TestDataEntriesRepository;

class FillMissingProjectDataEventTest {

	@Mock
    private ProjectsRepository repositoryProjects;
    @Mock
    private TestDataEntriesRepository repositoryTestDataEntries;
    @Mock
    private FftDataEntriesRepository repositoryFftDataEntries;
    @Mock
    private BugDataEntriesRepository repositoryBugDataEntries;
    @Mock
    private ApplicationEventPublisher eventPublisher;
	
    @InjectMocks
    private MissingProjectDataFiller dataFiller;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
	@Test
	void testOnEvent() {
		// Initialize projects
		Project project1 = new Project();
		project1.setName("Hermes II MXA123 5.1.X");
		project1.setReleaseName("1.0");
		
		Project project2 = new Project();
		project2.setName("Hermes II MXA456 5.1.X");
		project2.setIsActive(false);
		
		// Initialize data with a gap in between
		DataEntryId dataEntryId1 = new DataEntryId("2023-01-01", project1.getName());
		DataEntryId dataEntryId2 = new DataEntryId("2023-01-04", project1.getName());
		
		TestDataEntry testDataEntry1 = new TestDataEntry(dataEntryId1);
		TestDataEntry testDataEntry2 = new TestDataEntry(dataEntryId2);
		
		FftDataEntry fftDataEntry1 = new FftDataEntry(dataEntryId1);
		fftDataEntry1.setTestsLeft(10);
		FftDataEntry fftDataEntry2 = new FftDataEntry(dataEntryId2);

		BugDataEntry bugDataEntry1 = new BugDataEntry(dataEntryId1);
		BugDataEntry bugDataEntry2 = new BugDataEntry(dataEntryId2);

		// Add data to project's data lists
		project1.setTestDataEntries(new ArrayList<>(Arrays.asList(testDataEntry1, testDataEntry2)));
		project1.setFftDataEntries(new ArrayList<>(Arrays.asList(fftDataEntry1, fftDataEntry2)));
		project1.setBugDataEntries(new ArrayList<>(Arrays.asList(bugDataEntry1, bugDataEntry2)));
		
		// Mock repository dependency
		List<Project> projects = Arrays.asList(project1, project2);
		when(repositoryProjects.findAll()).thenReturn(projects);
		
		// Execute
		dataFiller.onEvent();
		
		// Verify that new data was saved
        verify(repositoryProjects).findAll();
        verify(repositoryProjects, times(6)).save(any(Project.class));
        verify(repositoryTestDataEntries, times(2)).save(any(TestDataEntry.class));
        verify(repositoryFftDataEntries, times(2)).save(any(FftDataEntry.class));
        verify(repositoryBugDataEntries, times(2)).save(any(BugDataEntry.class));
		
        assertThat(project1.getTestDataEntries().size()).isEqualTo(4);
        assertThat(project1.getFftDataEntries().size()).isEqualTo(4);
        assertThat(project1.getBugDataEntries().size()).isEqualTo(4);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
