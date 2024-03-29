package shure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shure.model.DataEntryId;
import shure.model.Project;
import shure.model.TestDataEntry;
import shure.repositories.ProjectsRepository;
import shure.repositories.TestDataEntriesRepository;

@Controller
@CrossOrigin
public class TestDataEntryControllers {

	@Autowired
	private TestDataEntriesRepository repository;
	@Autowired
	private ProjectsRepository repositoryProjects;

	@GetMapping("/api/v1/projects/{projectName}/test-data")
	public ResponseEntity<List<TestDataEntry>> getAll(@PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(project.get().getTestDataEntries());
	}

	@PostMapping("/api/v1/projects/{projectName}/test-data")
	public ResponseEntity<?> create(@RequestBody TestDataEntry testDataEntry, @PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if (project.get().addDataEntry(testDataEntry)) {
			repository.save(testDataEntry);
			repositoryProjects.save(project.get());
			return ResponseEntity.ok(testDataEntry);
		} else
			return ResponseEntity.badRequest()
					.body("{\"error\": \"An entry with ID " + testDataEntry.getDataEntryId() + " already exists!\"}");
	}

	@DeleteMapping("/api/v1/projects/{projectName}/test-data/{entryDate}")
	public ResponseEntity<?> delete(@PathVariable String projectName, @PathVariable String entryDate) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<TestDataEntry> testDataEntry = repository.findById(new DataEntryId(entryDate, projectName));
		if (testDataEntry.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		project.get().removeDataEntry(testDataEntry.get());
		repository.deleteById(new DataEntryId(entryDate, projectName));
		repositoryProjects.save(project.get());
		return ResponseEntity.noContent().build();
	}
}
