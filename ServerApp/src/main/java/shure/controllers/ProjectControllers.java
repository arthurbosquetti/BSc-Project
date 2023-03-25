package shure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

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
