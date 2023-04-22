package shure.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shure.model.Project;
import shure.repositories.ProjectsRepository;

@Controller
@CrossOrigin
public class ProjectControllers {

	@Autowired
	private ProjectsRepository repository;

	@GetMapping("/api/v1/projects")
	public ResponseEntity<List<Project>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@PostMapping("/api/v1/projects")
	public ResponseEntity<Object> create(@RequestBody Project project) {
		if (repository.existsById(project.getName())) {
			return ResponseEntity.badRequest().body("A project with name '" + project.getName() + "' already exists!");
		} else if (project.getNittanyUrl() == null) {
			return ResponseEntity.badRequest().body("The URL did not locate a valid Nitanny Report API page!");
		}
		return ResponseEntity.ok(repository.save(project));
	}

	@PutMapping("api/v1/projects/{name}")
	public ResponseEntity<Object> update(@RequestBody Project updatedProject, @PathVariable String name) {
		Optional<Project> project = repository.findById(name);
		if (project.isEmpty()) {
			return ResponseEntity.badRequest().body("Could not find a project with name '" + name + "'!");
		}
		if (!name.equals(updatedProject.getName())) {
			if (repository.existsById(updatedProject.getName())) {
				return ResponseEntity.badRequest().body("A project with name '" + updatedProject.getName() + "' already exists!");
			}
			repository.deleteById(name);
			project.get().setName(updatedProject.getName());
		}
		if (!updatedProject.getComponentsList().isEmpty()) {
			project.get().setComponentsList(updatedProject.getComponentsList());
		}
		project.get().setFftDeadline(updatedProject.getFftDeadline());
		project.get().updateStatus();
		return ResponseEntity.ok(repository.save(project.get()));
	}
	
	@DeleteMapping("/api/v1/projects/{name}")
	public ResponseEntity<?> delete(@PathVariable String name) {
		try {
			repository.deleteById(name);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.badRequest().body("Could not find a project with name '" + name + "'!");
		}
	}
}
