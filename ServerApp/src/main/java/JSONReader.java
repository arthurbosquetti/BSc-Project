import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

class JSONReader {	
	
	private URL url;
	private String json;

	public JSONReader(URL url) throws IOException {
		this.url = url;
		this.json = IOUtils.toString(url, Charset.forName("UTF-8"));
		System.out.println("JSON created!");
	}
	
	public void writeJSONToFile(String filePath) throws IOException {
		FileUtils.writeStringToFile(new File(filePath), json, Charset.forName("UTF-8"));
		System.out.println("Wrote JSON to file!");
	}
	
	
	

}
