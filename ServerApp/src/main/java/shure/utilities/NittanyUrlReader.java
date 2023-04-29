package shure.utilities;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class NittanyUrlReader {

	private URL url;
	JSONObject json;

	public NittanyUrlReader(String urlString) throws IOException {
		urlString = urlString.replace("report", "api/giftedReport").replaceAll(";cached=(true|false)", "/live/noFilter");
		this.url = new URL(urlString);
	}

	public boolean readUrl() throws IOException {
		System.out.println("Reading URL...");
		String urlOutput = IOUtils.toString(url, Charset.forName("UTF-8"));
		try {
			JSONObject json = new JSONObject(urlOutput);
			json.getJSONArray("line_chart_results");
			json.getJSONArray("all_bugs");
			json.getString("release_name");
			this.json = json;
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
