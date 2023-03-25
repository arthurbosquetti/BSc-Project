package shure.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import shure.model.DataEntry;
import shure.model.DataEntryId;

@NoRepositoryBean
public interface DataEntriesRepository<T extends DataEntry> extends CrudRepository<T, DataEntryId> {

}
