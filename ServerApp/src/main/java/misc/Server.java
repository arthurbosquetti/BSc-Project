package misc;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Server {
	public static void main(String[] args) throws MalformedURLException, IOException {
		
		long startTime = System.nanoTime();
		JSONReader jsonReader = new JSONReader(new URL("http://nittany.shure.com/api/giftedReport/Hermes/%25$Releases%25$5.0.X%25$MXA902%25$FFT/live/noFilter"));
		String filePath = "C:\\Users\\BosqueA\\OneDrive - shure\\Desktop\\BSc Project - Other data\\JSON Files\\test_file.json";
		jsonReader.readURL();
		jsonReader.writeJSONToFile(filePath);
		
		PyRunnerWithOutput pyRunnerWithOutput = new PyRunnerWithOutput();
		pyRunnerWithOutput.runner();
		long endTime = System.nanoTime();
		System.out.println("ran Server in " + (endTime-startTime)/1000000000 + "s!");
	}
}
