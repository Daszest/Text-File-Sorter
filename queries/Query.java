package softeng251.queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public abstract class Query
{
	// A String field for saving the File Name of the text file
	// It is protected because all subclasses of "Query" need access to to it
	protected String _fileID;
	
	
	// A String field for saving the Query Name of the text file
	// It is protected because all subclasses of "Query" need access to to it
	protected String _queryID;
	
	
	// An array of strings field for further decomposition of the text file
	// into a more manageable format
	// each piece of information in the Vector (up to 15, separated by "tab character" \t ) 
	// is assigned to an entry in the array _data  
	protected String[] _data = new String[15];
	
	
	// A list of "Module" types
	// Modules are objects representing dependencies that meet certain criteria
	protected List<Module> moduleList = new ArrayList<Module>();
	
	
	// An "integer" (primitive) field representing the index of a given object in the moduleList
	protected int listIndex = 0;
	
	
	// A field of a Set of Strings that is used for storing the names of unique source modules in order to find unique dependencies
	// Used in the implementation for most of the subclasses so I decided to move the common code into here.
	protected Set<String> _srcSet = new HashSet<String>();
	
	
	// A "Map" field that stores the name of a source module along with that module's position in the "moduleList" List
	// Used in the implementation for most of the subclasses so I decided to move the common code into here.
	protected Map<String, Integer> indexMap = new HashMap<String, Integer>();
	
	
	
	
	
	// A method that prints out the Type of Query and the File that was inquired about
	// Common to all subclasses
	public void outputStub()
	{
		//System.out.println("----- Output for " + _fileID + " " + _queryID + " --------");
		System.out.println("QUERY	" + _queryID);
		System.out.println("DATAID	" + _fileID);
	}
	
	
	// A method that prints out the Name of the dependency, the Type of Query and the File that was inquired about
	// Common to most subclasses
	public void output()
	{
		// Calls the outputStub method
		outputStub();
		
		// Checks if the list of modules ("moduleList") is empty or not
		if (!moduleList.isEmpty())
		{
			// If it is not then it loops through each object in the list and prints out (to the console)
			// The name of the dependency
			// The type of the dependency
			// The amount of times that that dependency matched certain criteria
			for(int i = 0; i < moduleList.size(); i++)
			{
				Module query = moduleList.get(i);
				System.out.println(query.nameOfModule() + " (" + query.typeOfModule() + ")" + "\t" + query.numberOfModules());
			}
		}
		// If the list of modules is empty, then it prints "No result" to the console
		// This should only happen if the input file was empty
		else if(moduleList.isEmpty())
		{
			System.out.println("No results");
		}
	}
	
	
	// A method that breaks up a line of the Scrubbed Data and puts each piece of data as a separate entry in the _data array
	// For example, Each dependency name will be put into the first entry of the _data field (_data[0])
	public void inquire(String queryCommand)
	{
		// Initializes an integer representing the position of the first character of a piece of data in a single line of the scrubbed data
		int currentStart = 0;
		
		// Initializes an integer representing the position of the last character of a piece of data in a single line of the scrubbed data
		int currentEnd = 0;
		
		// Initializes an integer representing the position of each piece of data in the _data array
		int index = 0;
		
		// Loops through the _data array 15 times
		for(int k = 0; k < 15; k++)
		{
			// Sets every value in the _data array to be a blank string initially
			_data[k] = "";
		}
		
		// Loops through ever character in a single line of the scrubbed data
		for(int i = 0; i < queryCommand.length(); i++)
		{
			// Checks if the character is a "tab" (\t) character
			// If it is, then make a substring of the line, starting from the last place a substring was created, until the tab character
			if(queryCommand.charAt(i) == '\t')
			{
				currentEnd = i;
				_data[index] = queryCommand.substring(currentStart, currentEnd);
				currentStart = currentEnd + 1;
				index++;
			}
			// Checks if the end of the line has been reached
			// If so, create a substring starting from the last place a substring was created, until the end of the line
			// This line is required because the end of the line does not contain a tab character
			if(i == queryCommand.length() - 1)
			{
				_data[index] = queryCommand.substring(currentStart);
			}
		}
	}
	
	
	// A method that returns true if a dependency has a target
	// Returns false otherwise
	public boolean moduleHasATarget()
	{
		return !_data[4].equals("");
	}
	
	
	// A method that returns true if a module (dependency) depends on itself
	// Returns false otherwise
	public boolean moduleDependsOnItself()
	{
		return _data[4].equals(_data[0]);
	}
	
	
	// A method that returns a String containing the name of the module with a dependency
	public String moduleName()
	{
		return _data[0];
	}
	
	
	// A method that returns a String containing the name of the module that is being depended on
	public String targetName()
	{
		return _data[4];
	}
	
	
	// A method that returns a String containing the type of the module with a dependency
	public String kindOfModule()
	{
		return _data[2];
	}
	
	
	// A method that returns a String containing the usage details of a dependency
	// For example: The name of the method used from the dependency
	public String moduleUseDetails()
	{
		return _data[9];
	}
	
	
	// A method that returns a String containing a module's dependency category
	// For example: if it accessed a static field, or invoked an instance method etc
	public String moduleCategory()
	{
		return _data[7];
	}
}
