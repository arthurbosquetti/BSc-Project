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
import shure.model.FftDataEntry;
import shure.model.Project;
import shure.repositories.FftDataEntriesRepository;
import shure.repositories.ProjectsRepository;

@Controller
@CrossOrigin
public class FftDataEntryControllers {

	@Autowired
	private FftDataEntriesRepository repository;
	@Autowired
	private ProjectsRepository repositoryProjects;

	@GetMapping("/api/v1/projects/{projectName}/fft-data")
	public ResponseEntity<List<FftDataEntry>> getAll(@PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(project.get().getFftDataEntries());
	}

	@PostMapping("/api/v1/projects/{projectName}/fft-data")
	public ResponseEntity<?> create(@RequestBody FftDataEntry fftDataEntry, @PathVariable String projectName) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if (project.get().addDataEntry(fftDataEntry)) {
			repository.save(fftDataEntry);
			repositoryProjects.save(project.get());
			return ResponseEntity.ok(fftDataEntry);
		} else
			return ResponseEntity.badRequest()
					.body("{\"error\": \"An entry with ID " + fftDataEntry.getDataEntryId() + " already exists!\"}");
	}

	@DeleteMapping("/api/v1/projects/{projectName}/fft-data/{entryDate}")
	public ResponseEntity<?> delete(@PathVariable String projectName, @PathVariable String entryDate) {
		Optional<Project> project = repositoryProjects.findById(projectName);
		if (project.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<FftDataEntry> fftDataEntry = repository.findById(new DataEntryId(entryDate, projectName));
		if (fftDataEntry.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		project.get().removeDataEntry(fftDataEntry.get());
		repository.deleteById(new DataEntryId(entryDate, projectName));
		repositoryProjects.save(project.get());
		return ResponseEntity.noContent().build();
	}
}
