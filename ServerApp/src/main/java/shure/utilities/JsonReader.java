package shure.utilities;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

	private URL url;
	JSONObject json;

	public JsonReader(URL url) throws IOException {
		this.url = url;
	}

	public boolean readUrl() throws IOException {
		System.out.println("Reading URL...");
		String urlOutput = IOUtils.toString(url, Charset.forName("UTF-8"));
		try {
			this.json = new JSONObject(urlOutput);
			System.out.println("URL reading successful!");
			return true;
		} catch (JSONException e) {
			System.out.println("URL reading failed!");
			return false;
		}
	}

	public JSONObject getJson() {
		return json;
	}

}
