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
import shure.model.FftDataEntry;
import shure.model.Project;
import shure.repositories.FftDataEntriesRepository;
import shure.repositories.ProjectsRepository;

@WebMvcTest(FftDataEntryControllers.class)
class FftDataEntryControllersTest {

	@Autowired
    private MockMvc mockMvc;
	@MockBean
	private FftDataEntriesRepository repositoryFftDataEntries;
	@MockBean
	private ProjectsRepository repositoryProjects;
	private Project project;
	private FftDataEntry fftDataEntry1;
	private String apiBaseUrl = "/api/v1/";

	
	@BeforeEach
	void setUp() throws Exception {
		project = new Project();
		project.setName("Hermes II MXA123 5.1.X");
		
		fftDataEntry1 = new FftDataEntry(new DataEntryId("2023-01-01", project.getName()));
		fftDataEntry1.setTestsLeft(1);
		
	}

	@Test
	void testGetAll() throws Exception {
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(get(this.apiBaseUrl + "projects/" + project.getName() + "/fft-data"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testCreate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(fftDataEntry1);
		
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
				
		this.mockMvc.perform(post(this.apiBaseUrl + "projects/" + project.getName() + "/fft-data")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void testDelete() throws Exception {
		when(repositoryProjects.findById(project.getName())).thenReturn(Optional.of(project));
		when(repositoryFftDataEntries.findById(fftDataEntry1.getDataEntryId())).thenReturn(Optional.of(fftDataEntry1));

		doNothing().when(repositoryProjects).deleteById(project.getName());
		doNothing().when(repositoryFftDataEntries).deleteById(fftDataEntry1.getDataEntryId());
        this.mockMvc.perform(delete(this.apiBaseUrl + "projects/" + project.getName() + "/fft-data/" + fftDataEntry1.getDataEntryId().getEntryDate()));
	}
	
}