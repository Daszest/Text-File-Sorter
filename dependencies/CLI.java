package softeng251.dependencies;

import java.io.IOException;

//Used a global import statement since i need all the classes from the 'query' Class
import softeng251.queries.*;

public class CLI
{
	public static void main(String[] args) throws IOException
	{
		// The following 3 lines of code load in the file so that my program can process it.
		FileScan file = new FileScan(args[0]);
		file.OpenFile();
		String fID = file.fileID();
		
		
		// Gets the number of lines that need to be processed
		int lineAmount = file.getLineAmount();
		
		
		// The following 'if statement' block selects what constructor needs to be called.
		// If there is an error in query selection, an exception will be thrown.
		// For simplicity, I used a runtime exception since crashing the code with an appropriate 
		// error message is sufficient.
		Query service = null; 
		if(args[1].equals("Summary"))
		{
			service = new Summary(fID, args[1]);
		}
		
		else if(args[1].equals("DepCount"))
		{
			service = new DepCount(fID, args[1]);
		}
		
		else if(args[1].equals("FanOut"))
		{
			service = new FanOut(fID, args[1]);
		}
		
		else if(args[1].equals("FanIn"))
		{
			service = new FanIn(fID, args[1]);
		}
		
		else if(args[1].equals("Uses"))
		{
			service = new Uses(fID, args[1]);
		}
		
		else if(args[1].equals("Static"))
		{
			service = new Static(fID, args[1]);
		}
		
		else if(args[1].equals("Aggregates"))
		{
			service = new Aggregates(fID, args[1]);
		}
			
		else
		{
			throw new RuntimeException("Incorrect Query entered. Please choose one query from the following (case sensitive): Summary, DepCount, FanOut, FanIn, Uses, Static, Aggregates");
		}
		
		
		// Where the magic happens.
		// Processes the file, line by line.
		// All the above classes are subclasses of the 'Query' class 
		// The query class has an inquire method which has some default implementation
		for(int i = 0; i < lineAmount; i++)
		{
			String currentLine = file.getLine(i);
			service.inquire(currentLine);
		}
		
		
		// Outputs the information in the specified format
		service.output();
	}
}
