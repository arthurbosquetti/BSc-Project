package shure.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shure.model.Project;

@Repository
public interface ProjectsRepository extends CrudRepository<Project, String>{
	
	List<Project> findAll();
}
