package shure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import shure.model.DataEntry;

@NoRepositoryBean
public interface DataEntriesRepository<T extends DataEntry> extends CrudRepository<T, String>{
	
}
