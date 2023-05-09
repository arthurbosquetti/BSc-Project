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
		String urlOutput = IOUtils.toString(url, Charset.forName("UTF-8"));
		try {
			JSONObject json = new JSONObject(urlOutput);
			json.getJSONArray("line_chart_results");
			json.getJSONArray("tc_results");
			json.getJSONArray("all_bugs");
			this.json = json;
			return true;
		} catch (JSONException e) {
			return false;
		}
	}

	public JSONObject getJson() {
		return json;
	}

}
