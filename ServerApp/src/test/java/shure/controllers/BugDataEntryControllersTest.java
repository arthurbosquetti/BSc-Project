package shure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import shure.model.DataEntryId;
import shure.model.BugDataEntry;
import shure.model.Project;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.ProjectsRepository;

@WebMvcTest(BugDataEntryControllers.class)
class BugDataEntryControllersTest {

	@Autowired
    private MockMvc mockMvc;
	@MockBean
	private BugDataEntriesRepository repositoryBugDataEntries;
	@MockBean
	private ProjectsRepository repositoryProjects;
	private Project project;
	private BugDataEntry bugDataEntry1;
	private String apiBaseUrl = "/api/v1/";

	
	@BeforeEach
	void setUp() throws Exception {
		project = new Project();
		project.setName("Hermes II MXA123 5.1.X");
		
		bugDataEntry1 = new BugDataEntry(new DataEntryId("2023-01-01", project.getName()));
		
	}

	@Test
	void testGetAll() throws Exception {
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(get(this.apiBaseUrl + "projects/" + project.getName() + "/bug-data"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testCreate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(bugDataEntry1);
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(post(this.apiBaseUrl + "projects/" + project.getName() + "/bug-data")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testDelete() throws Exception {
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
		when(repositoryBugDataEntries.findById(bugDataEntry1.getDataEntryId())).thenReturn(Optional.of(bugDataEntry1));

		doNothing().when(repositoryProjects).deleteById(project.getName());
		doNothing().when(repositoryBugDataEntries).deleteById(bugDataEntry1.getDataEntryId());
        this.mockMvc.perform(delete(this.apiBaseUrl + "projects/" + project.getName() + "/bug-data/" + bugDataEntry1.getDataEntryId().getEntryDate()));
	}
	
}