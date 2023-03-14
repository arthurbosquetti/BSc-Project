package shure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import shure.model.DataEntry;

@Repository
public interface DataEntriesRepository<T extends DataEntry> extends CrudRepository<T, String>{

}
