package softeng251.dependencies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class FileScan 
{
	// A field for saving the File Path for the input text file
	private String _path;
	
	// A vector containing each line of the file as one entry in the vector.
	// I could have used an arrayList but I think vectors are cool
	// Also we aren't marked on running efficiency :)
	private Vector<String> _scrubbedData;
	
	
	
	// Constructor for the FileScan object
	// Takes in a string representing a File Name
	public FileScan(String file_path)
	{
		_path = file_path;
	}
	
	
	// The 'Heavy-lifting' method in the FileScan Class
	// Allows the program to recognize the input file
	// Saves each line of the file to a separate entry in the _scrubbedData field
	public void OpenFile() throws IOException
	{
		try
		{
			// Initializes a FileReader using the _path field (File Path) as input
			FileReader reader = new FileReader(_path);
			// Initializes a BufferedReader using the FileReader as an input
			BufferedReader buffer = new BufferedReader(reader);
			// Initializes a Vector to store the lines of the file
			Vector<String> depData = new Vector<String>();
			// Initializes the field _scrubbedData to a vector as well.
			// This field only stores the relevant information that needs to be processed
			// Hence "Scrubbed Data"
			_scrubbedData = new Vector<String>();
			
			// Initializes a string Line
			String Line;
			
			// Loops through every line until the end of the text file (null is reached)
			while((Line = buffer.readLine()) != null)
			{
				// Adds the line to the depData vector, this is the raw (unscrubbed) text
				depData.add(Line);
			}
			
			// This enhanced for loop then scrubs the data
			// First it loops through the raw data
			for(String j : depData)
			{
				// Checks if the line is empty 
				if(j.length() > 0)
				{
					// Checks for the first character for a hashtag
					// A hashtags at the start of each line indicates that the line does not contain any data
					if(j.charAt(0) != '#')
					{
						// Adds only the data to the vector field _scrubbedData
						_scrubbedData.add(j);
					}
				}
			}
			// Closes the buffer stream
			buffer.close();
		}
		
		// If the input file is specified wrong, an error message will occur telling you so
		catch (IOException except)
		{
			System.out.println(except.getMessage());
		}
	}
	
	
	// Method that takes in an integer representing which line of the data that needs to be accessed
	// Returns a single string, containing the corresponding line of the data
	// for example an input of 3 will return the third line of the "Scrubbed Data"
	public String getLine(int lineNum)
	{
		return _scrubbedData.get(lineNum);
	}
	
	
	// Method that returns the number of Lines that will need to be processed
	public int getLineAmount()
	{
		return _scrubbedData.size();
	}
	
	
	// Method that returns the name of the file by using the path name
	public String fileID()
	{
		// Starts looping from the end of the path name
		for (int i = _path.length() - 1; i >= 0; i-- )
		{
			// Checks for a backslash in the path name
			if (_path.charAt(i) == '\\')
			{
				//
				return _path.substring(i + 1, _path.length());
			}
		}
		// Default return statement, must be included to keep compiler happy
		// Pretty sure that the "if statement" will be always satisfied
		return _path;
	}
}
