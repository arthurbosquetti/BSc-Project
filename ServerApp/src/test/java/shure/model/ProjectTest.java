package shure.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectTest {

	Project project;
	FftDataEntry fftDataEntry1;
	FftDataEntry fftDataEntry2;
	FftDataEntry fftDataEntry3;
	FftDataEntry fftDataEntry4;
	
	
	@BeforeEach
	void setUp() {
		
		project = new Project();
		project.setName("MXA123 1.0.X");
        project.setFftDeadline("2023-01-05");
        project.setFftDataEntries(new ArrayList<>());
        
        fftDataEntry1 = new FftDataEntry(new DataEntryId("2023-01-01", project.getName()));
        fftDataEntry1.setTestsLeft(150);
        fftDataEntry2 = new FftDataEntry(new DataEntryId("2023-01-02", project.getName()));
        fftDataEntry2.setTestsLeft(150);
        fftDataEntry3 = new FftDataEntry(new DataEntryId("2023-01-03", project.getName()));
        fftDataEntry3.setTestsLeft(40);
        fftDataEntry4 = new FftDataEntry(new DataEntryId("2023-01-04", project.getName()));
        fftDataEntry4.setTestsLeft(30);


	}

	@Test
	void testSetBlankFftDeadline() {
		project.setFftDeadline("");
		assertThat(project.getFftDeadline()).isNull();
	}
	
	@Test
	void testUpdateStatus() {
		project.addDataEntry(fftDataEntry1);
		project.addDataEntry(fftDataEntry2);
		project.addDataEntry(fftDataEntry3);
		project.addDataEntry(fftDataEntry4);
		assertThat(project.getFftStatus()).isEqualTo(FftStatus.ON_TRACK);
	}

}
