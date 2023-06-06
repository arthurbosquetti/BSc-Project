package shure.controllers;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import shure.model.Project;
import shure.repositories.ProjectsRepository;

@WebMvcTest(ProjectControllers.class)
class ProjectControllersTest {

	@Autowired
    private MockMvc mockMvc;
	@MockBean
    private ProjectsRepository repositoryProjects;
	private Project project1;
	private Project project2;
	private List<Project> projectList = new ArrayList<Project>();
	
	private String apiBaseUrl = "/api/v1/";
	private String nittanyDataUrl = String.format("file://%s/src/test/resources/data/nittany_data.json", 
    		Paths.get("").toAbsolutePath().toString());
	
	@BeforeEach
	void setUp() throws Exception {
		project1 = new Project();
		project1.setName("Hermes II MXA123 5.1.X");
		project1.setNittanyUrl(nittanyDataUrl);
		
		project2 = new Project();
		project2.setName("Hermes II MXA456 5.1.X");
		
		projectList.add(project1);
		projectList.add(project2);
				
	}

	@Test
	void testGetAll() throws Exception {
		when(repositoryProjects.findAll()).thenReturn(projectList);
        this.mockMvc.perform(get(this.apiBaseUrl + "/projects"))
                .andDo(print()).andExpect(status().isOk());

	}
	
	
	@Test
	void testCreate_Valid() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project1);
		        
        when(repositoryProjects.existsById(project1.getName())).thenReturn(false);
		when(repositoryProjects.save(project1)).thenReturn(project1);
        
		this.mockMvc.perform(post(this.apiBaseUrl + "/projects")
					.contentType(MediaType.APPLICATION_JSON)
			        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	void testCreate_InvalidName() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project1);
		        
        when(repositoryProjects.existsById(project1.getName())).thenReturn(true);
        
		this.mockMvc.perform(post(this.apiBaseUrl + "/projects")
					.contentType(MediaType.APPLICATION_JSON)
			        .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest());

	}
	
	@Test
	void testCreate_InvalidNittanyUrl() throws Exception {
		project2.setNittanyUrl("example.com/");

		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project2);
		        
        when(repositoryProjects.existsById(project2.getName())).thenReturn(false);
        
		this.mockMvc.perform(post(this.apiBaseUrl + "/projects")
					.contentType(MediaType.APPLICATION_JSON)
			        .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest());

	}
	
	@Test
	void testUpdate_ValidName() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project1);

        when(repositoryProjects.findById(project1.getName())).thenReturn(Optional.ofNullable(project1));
		when(repositoryProjects.save(project1)).thenReturn(project1);
        
		this.mockMvc.perform(patch(this.apiBaseUrl + "/projects")
					.contentType(MediaType.APPLICATION_JSON)
			        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testUpdate_InvalidName() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(project1);

        when(repositoryProjects.findById(project1.getName())).thenReturn(Optional.ofNullable(null));
		when(repositoryProjects.save(project1)).thenReturn(project1);
        
		this.mockMvc.perform(patch(this.apiBaseUrl + "/projects")
					.contentType(MediaType.APPLICATION_JSON)
			        .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest());

	}
	
	@Test
	void testGetProject_ValidName() throws Exception {
		when(repositoryProjects.findById(project1.getName())).thenReturn(Optional.ofNullable(project1));
        this.mockMvc.perform(get(this.apiBaseUrl + "/projects/" + project1.getName()))
                .andDo(print()).andExpect(status().isOk());

	}
	
	@Test
	void testGetProject_InvalidName() throws Exception {
		when(repositoryProjects.findById(project1.getName())).thenReturn(Optional.ofNullable(null));
        this.mockMvc.perform(get(this.apiBaseUrl + "/projects/" + project1.getName()))
                .andDo(print()).andExpect(status().isBadRequest());

	}
	
	@Test
	void testDelete_ValidName() throws Exception {
		doThrow(new EmptyResultDataAccessException(0))
			.when(repositoryProjects).deleteById(anyString());
        this.mockMvc.perform(delete(this.apiBaseUrl + "/projects/" + project1.getName()));

	}
	
	@Test
	void testDelete_InalidName() throws Exception {
		doNothing().when(repositoryProjects).deleteById(project1.getName());
        this.mockMvc.perform(delete(this.apiBaseUrl + "/projects/" + project1.getName()));
	}

}
