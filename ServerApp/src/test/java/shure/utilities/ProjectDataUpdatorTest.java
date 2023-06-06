package shure.utilities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import shure.model.BugDataEntry;
import shure.model.FftDataEntry;
import shure.model.Project;
import shure.model.TestDataEntry;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.FftDataEntriesRepository;
import shure.repositories.ProjectsRepository;
import shure.repositories.TestDataEntriesRepository;

class ProjectDataUpdatorTest {

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
    
    @Captor
    private ArgumentCaptor<FillMissingProjectDataEvent> eventCaptor;
    
    @InjectMocks
    private ProjectDataUpdator projectDataUpdator;
    
    private String url = String.format("file://%s/src/test/resources/data/nittany_data.json", 
    		Paths.get("").toAbsolutePath().toString());
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	void testOnSchedule() throws MalformedURLException, IOException {
		
		// Initialize objects for this test
		Project project1 = new Project();
		project1.setName("Hermes II MXA123 5.1.X");
		project1.setReleaseName("1.0");
		project1.setNittanyUrl(url);
		project1.setComponentsList(Arrays.asList("all"));
		
		Project project2 = new Project();
		project2.setName("Hermes II MXA456 5.1.X");
		project1.setReleaseName("1.0");
		project2.setNittanyUrl(url);
		project2.setIsActive(false);
		
		// Mock repository dependency
		List<Project> projects = Arrays.asList(project1, project2);
        when(repositoryProjects.findAll()).thenReturn(projects);
        
        projectDataUpdator.onSchedule();
		
        // Verify that new data was saved
        verify(repositoryProjects).findAll();
        verify(repositoryProjects).save(any(Project.class));
        verify(repositoryTestDataEntries).save(any(TestDataEntry.class));
        verify(repositoryFftDataEntries).save(any(FftDataEntry.class));
        verify(repositoryBugDataEntries).save(any(BugDataEntry.class));
		
        // Verify that the FillMissingProjectDataEvent was published
        assertThat(projectDataUpdator.getEventPublished()).isTrue();
        verify(eventPublisher).publishEvent(eventCaptor.capture());
		assertThat(FillMissingProjectDataEvent.class).isEqualTo(eventCaptor.getValue().getClass());
        
	}

}
