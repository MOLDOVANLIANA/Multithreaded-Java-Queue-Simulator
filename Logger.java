import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	private static Logger Instance = new Logger();
	private FileWriter Writer;

	public static Logger Get() {
		return Instance;
	}

	private Logger() {

	}

	public void InitializeWriter(String FileName) {
		try {
			File CurrentFile = new File(FileName);
			if (!CurrentFile.exists())
				CurrentFile.createNewFile();

			Writer = new FileWriter(CurrentFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	
	void Println(String Text)
	{
		try {
			Writer.write(Text + "\n");
			Writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
