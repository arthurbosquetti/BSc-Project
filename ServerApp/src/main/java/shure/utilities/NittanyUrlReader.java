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

/*
NITTANY URL: http://nittany.shure.com/  report/  			Hermes/%25$Releases%25$5.0.X%25$MXA902%25$FFT	;cached=false
API 	URL: http://nittany.shure.com/  api/giftedReport/	Hermes/%25$Releases%25$5.0.X%25$MXA902%25$FFT	/live/noFilter

NITTANY URL: http://nittany.shure.com/	report/				Hermes/%25$Releases%25$5.0.X%25$MXA902%25$FFT	;cached=true
API     URL: http://nittany.shure.com/	api/giftedReport/	Hermes/%25$Releases%25$5.0.X%25$MXA902%25$FFT	/cached/noFilter




*/