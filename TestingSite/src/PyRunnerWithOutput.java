import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PyRunnerWithOutput {
	public static void main(String[] args) throws IOException {
		
		String[] cmd = {"python", 
				"C:\\Users\\Bosquea\\OneDrive - shure\\Desktop\\BSc Project\\jsonReader.py", 
				"C:\\Users\\BosqueA\\OneDrive - shure\\Desktop\\BSc Project\\big_file.json"};
		Process process = Runtime.getRuntime().exec(cmd);
		
		String s;
		
		BufferedReader stdInput = new BufferedReader(new 
                InputStreamReader(process.getInputStream()));

           BufferedReader stdError = new BufferedReader(new 
                InputStreamReader(process.getErrorStream()));

           // read the output from the command
           System.out.println("Here is the standard output of the command:\n");
           while ((s = stdInput.readLine()) != null) {
               System.out.println(s);
           }
           
           // read any errors from the attempted command
           System.out.println("\nHere is the standard error of the command (if any):\n");
           while ((s = stdError.readLine()) != null) {
               System.out.println(s);
           }
	}
}
