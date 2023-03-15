package shure.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataEntry {
	@Id
	@Column
	private String entryDate;
	
	public String getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataEntry) {
			return ((DataEntry) obj).getEntryDate().equals(entryDate);
		}
		return false;
	}
	
//	@Override
//	// Hashcode function
	
	
}
