package shure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		if (repository.findAll().contains(project)) {
			return ResponseEntity.badRequest().body("{\"error\": \"A project with name '" + project.getName() + "' already exists!\"}");
		}
		return ResponseEntity.ok(repository.save(project));
	}
	
	@DeleteMapping("/api/v1/projects/{name}")
	public ResponseEntity<?>delete(@PathVariable String name) {
		repository.deleteById(name);
		return ResponseEntity.noContent().build();
	}
	
}

















