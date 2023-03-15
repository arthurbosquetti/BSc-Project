package shure.model;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataEntry {
	
	@EmbeddedId
	private DataEntryId dataEntryId;
	
	public DataEntryId getDataEntryId() {
		return dataEntryId;
	}

	public void setDataEntryId(DataEntryId dataEntryId) {
		this.dataEntryId = dataEntryId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataEntry) {
			return ((DataEntry) obj).getDataEntryId().equals(dataEntryId);
		}
		return false;
	}
	
//	@Override
//	// Hashcode function
	
	
}
