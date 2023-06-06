package shure.utilities;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NittanyUrlReaderTest {

	private String url = "";
	
	@BeforeEach
	void setUp() {
		url = "file://" + Paths.get("").toAbsolutePath().toString() + "/src/test/resources/data/";
	}
	
	@Test
	void testReadUrl_Success() throws IOException {
		// Given
		NittanyUrlReader reader = new NittanyUrlReader(url + "complete_data.json");
		
		// When
		reader.readUrl();

		// Then
		assertThat(reader.getJson()).isNotNull();
	}
	
	@Test
	void testReadUrl_Fail() throws IOException {
		// Given
		NittanyUrlReader reader = new NittanyUrlReader(url + "incomplete_data.json");
		
		// When
		reader.readUrl();
		
		// Then
		assertThat(reader.getJson()).isNull();
	}
	
}
