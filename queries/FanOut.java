package softeng251.queries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FanOut extends Query
{
	// A "Set" field that is used for keeping a record of each unique dependency that a module is dependent on
	private Set<String> _fanOutSet = new HashSet<String>();
	
	
	
	
	
	// A constructor that takes in two strings fileID and queryID as inputs and sets the _fileID and _queryID fields to those Strings respectively
	// Creates a FanOut object
	public FanOut(String fileID, String queryID)
	{
		super._fileID = fileID;
		super._queryID = queryID;
	}
	
	
	// Default constructor for a FanOut object
	public FanOut()
	{
	}
	
	
	
	
	
	// A method that does all the heavy lifting for the FanOut query
	// The FanOut query is supposed to list all source modules declared in the text file along with the number of unique dependencies each of those modules has
	// The order of the list of source modules is the natural ordering of "Module" objects (using "compareTo" method in class "Module")
	// This method does all the behind scenes work for FanOut, that is it does everything except for printing the information to the console and the sorting
	// Those two actions are done in the "output" method
	public void inquire(String QueryCommand)
	{
		// Calls the inquire method from the abstract class "Query"
		super.inquire(QueryCommand);
		
		// Creates a string that is a concatenation of the name of the declared module and its target dependency
		String tempConcat = moduleName() + targetName();
		
		// Tries to add the name of the module to the set of source modules
		// If successful (only fails if the module is already present):
		// Makes a "Module" object and puts it into "moduleList" at the position of "listIndex"
		// Puts the same name along with it's position in "moduleList" as the "Key" and "Value" values in a map respectively
		// listIndex is incremented each time to reflect the positional change for the next DepCount object added
		if(_srcSet.add(moduleName()))
		{
			moduleList.add(new Module(kindOfModule(), moduleName()));
			indexMap.put(moduleName(), listIndex);
			listIndex++;	
		}
		
		// Checks if the module has already been added to the set of Source Modules and it has a dependency
		// Also checks if the module depends on itself and if the module has not already depended on the target dependency in question
		// If the above conditions are met then we increase the counter in the module object
		// This is done by getting the index of the module from the tree 
		// and using it to find the appropriate module in the ArrayList "moduleList"
		// The increase method is then invoked on the object
		if(!_srcSet.add(moduleName()) && moduleHasATarget())
		{
			if(!moduleDependsOnItself())
			{
				if(_fanOutSet.add(tempConcat))
				{
					int tempIndex = indexMap.get(moduleName());
					moduleList.get(tempIndex).increase();
				}
			}
		}
	}
	
	
	// A method that prints the output of the "FanOut" query to the command line
	public void output()
	{
		// Sorts "moduleList" according to the natural ordering defined in the Module Class (using the "compareTo" method)
		Collections.sort(moduleList);

		// Calls the output method from the "Query" superclass
		super.output();
	}
}
