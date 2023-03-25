package shure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shure.model.Project;
import shure.model.BugDataEntry;
import shure.model.DataEntryId;
import shure.repositories.BugDataEntriesRepository;
import shure.repositories.ProjectsRepository;

@Controller
@CrossOrigin
public class BugDataEntryControllers {

	@Autowired
	private BugDataEntriesRepository repository;
	@Autowired
	private ProjectsRepository repositoryProjects;

	@GetMapping("/api/v1/projects/{projectName}/bug-data")
	public ResponseEntity<List<BugDataEntry>> getAll(@PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(project.get().getBugDataEntries());
	}

	@PostMapping("/api/v1/projects/{projectName}/bug-data")
	public ResponseEntity<?> create(@RequestBody BugDataEntry bugDataEntry, @PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		try {
			project.get().addDataEntry(bugDataEntry);
			repositoryProjects.save(project.get());
			return ResponseEntity.ok(repository.save(bugDataEntry));
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest()
					.body("{\"error\": \"An entry with ID " + bugDataEntry.getDataEntryId() + " already exists!\"}");
		}
	}

	@DeleteMapping("/api/v1/projects/{projectName}/bug-data/{entryDate}")
	public ResponseEntity<?> delete(@PathVariable String projectName, @PathVariable String entryDate) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<BugDataEntry> bugDataEntry = repository.findById(new DataEntryId(entryDate, projectName));
		if (bugDataEntry.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		project.get().removeDataEntry(bugDataEntry.get());
		repository.deleteById(new DataEntryId(entryDate, projectName));
		repositoryProjects.save(project.get());
		return ResponseEntity.noContent().build();
	}
}
