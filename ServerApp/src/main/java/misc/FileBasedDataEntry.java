package misc;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;


public abstract class FileBasedDataEntry {

	@Id
	@Column
	private String entryDate;
	@Column
	private String projectName;
	@Column
	private String[] fieldNames;
	@Column
	private List<Integer> dataValues;
	
	public String getEntryDate() {
		return this.entryDate;
	}
	
	public String getCsvLine() {
		return entryDate + ", " + StringUtils.join(dataValues, ", ");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FileBasedDataEntry) {
			return (this.entryDate).equals(((FileBasedDataEntry) obj).getEntryDate());
		}
		return false;
	}

	
	
	
	
}
