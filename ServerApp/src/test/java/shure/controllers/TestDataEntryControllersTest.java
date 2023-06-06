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
import shure.model.TestDataEntry;
import shure.model.Project;
import shure.repositories.TestDataEntriesRepository;
import shure.repositories.ProjectsRepository;

@WebMvcTest(TestDataEntryControllers.class)
class TestDataEntryControllersTest {

	@Autowired
    private MockMvc mockMvc;
	@MockBean
	private TestDataEntriesRepository repositoryTestDataEntries;
	@MockBean
	private ProjectsRepository repositoryProjects;
	private Project project;
	private TestDataEntry testDataEntry1;
	private String apiBaseUrl = "/api/v1/";

	
	@BeforeEach
	void setUp() throws Exception {
		project = new Project();
		project.setName("Hermes II MXA123 5.1.X");
		
		testDataEntry1 = new TestDataEntry(new DataEntryId("2023-01-01", project.getName()));
		
	}

	@Test
	void testGetAll() throws Exception {
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(get(this.apiBaseUrl + "projects/" + project.getName() + "/test-data"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testCreate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(testDataEntry1);
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(post(this.apiBaseUrl + "projects/" + project.getName() + "/test-data")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testDelete() throws Exception {
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
		when(repositoryTestDataEntries.findById(testDataEntry1.getDataEntryId())).thenReturn(Optional.of(testDataEntry1));

		doNothing().when(repositoryProjects).deleteById(project.getName());
		doNothing().when(repositoryTestDataEntries).deleteById(testDataEntry1.getDataEntryId());
        this.mockMvc.perform(delete(this.apiBaseUrl + "projects/" + project.getName() + "/test-data/" + testDataEntry1.getDataEntryId().getEntryDate()));
	}
	
}