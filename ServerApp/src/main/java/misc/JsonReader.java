package misc;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {	
	
	private URL url;
	String jsonString;

	public JsonReader(URL url) throws IOException {
		this.url = url;
	}
	
	public boolean readUrl() throws IOException {
		System.out.println("Reading URL...");
		String urlOutput = IOUtils.toString(url, Charset.forName("UTF-8"));
		if (isValid(urlOutput)) {
			System.out.println("The URL output is a JSON String!");
			this.jsonString = urlOutput;
			return true;
		}
		System.out.println("The URL output is not a JSON String!");
		return false;
	}
	
	private boolean isValid(String urlOutput) {
		try {
	        new JSONObject(urlOutput);
	    } catch (JSONException e) {
	        return false;
	    }
	    return true;
	}
	
	public void writeJsonToFile(String filePath) throws IOException {
		FileUtils.writeStringToFile(new File(filePath), jsonString, Charset.forName("UTF-8"));
		System.out.println("Wrote JSON to file!");
	}
	
	public String getJsonString() {
		return jsonString;
	}
	

}
