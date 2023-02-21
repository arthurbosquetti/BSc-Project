import java.io.IOException;

public class PyRunner {
	public static void main(String[] args) throws IOException {
		String[] cmd = {"python", 
				"C:\\Users\\Bosquea\\OneDrive - shure\\Desktop\\BSc Project\\untitled0.py", 
				"C:\\Users\\BosqueA\\OneDrive - shure\\Desktop\\BSc Project\\coursebase.json"};
		Runtime.getRuntime().exec(cmd);
		
		System.out.println("Completed!");
	}
}

