package misc;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class FileBasedProject {
	
	@Id
	@Column
	private String name;
	@Column
	private String fftDeadline;

	public void appendDataEntry(FileBasedDataEntry dataEntry, String dataFilePath) throws IOException, CsvException {
		CSVReader reader = new CSVReader(new FileReader(dataFilePath));
		List<String[]> allEntries = reader.readAll();
		allEntries.add(dataEntry.getCsvLine().split(", "));
		writeDataEntries(allEntries, dataFilePath);
	}
	
	public void removeDataEntry(FileBasedDataEntry dataEntry, String dataFilePath) throws IOException, CsvException {
		CSVReader reader = new CSVReader(new FileReader(dataFilePath));
		List<String[]> allEntries = reader.readAll();
		
		for (String[] entry : allEntries) {
			if (entry[0].equals(dataEntry.getEntryDate())) {
				allEntries.remove(entry);
				break;
			}
		}
		writeDataEntries(allEntries, dataFilePath);
	}
	
	private void writeDataEntries(List<String[]> dataEntries, String dataFilePath) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(dataFilePath));
		writer.writeAll(dataEntries);
		writer.close();
	}
	
	
}
































