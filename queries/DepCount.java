package softeng251.queries;

import java.util.Collections;

public class DepCount extends Query
{	
	// A constructor that takes in two strings fileID and queryID as inputs and sets the _fileID and _queryID fields to those Strings respectively
	// Creates a DepCount object
	public DepCount(String fileID, String queryID)
	{
		super._fileID = fileID;
		super._queryID = queryID;
	}
	
	
	// Default constructor for a DepCount object
	public DepCount()
	{	
	}
	
	
	
	
	
	// A method that adds every unique module into a set
	public void inquire(String QueryCommand)
	{
		// Calls the inquire method from the abstract class "Query"
		super.inquire(QueryCommand);
		
		// Tries to add the name of the module to the set of source modules
		// If successful (only fails if the module is already present):
		// Makes a "Module" object and puts it into "moduleList" at the position of "listIndex"
		// Puts the same name along with it's position in "moduleList" as the "Key" and "Value" values in a map respectively
		// listIndex is incremented each time to reflect the positional change for the next DepCount object added
		if(_srcSet.add(moduleName()))
		{
			indexMap.put(moduleName(), listIndex);
			listIndex++;
			moduleList.add(new Module(kindOfModule(), moduleName()));
		}
		
		// Checks if the module has already been added to the set of Source Modules
		// If it has been added and it has a dependency, then we increase the counter in the module object
		// This is done by getting the index of the module from the tree 
		// and using it to find the appropriate module in the ArrayList "moduleList"
		// The increase method is then invoked on the object
		if(!_srcSet.add(moduleName()) && moduleHasATarget())
		{
			int tempIndex = indexMap.get(moduleName());
			moduleList.get(tempIndex).increase();
		}
	}
	
	
	// A method that prints the output of the "DepCount" query to the command line
	public void output()
	{
		// Sorts "moduleList" according to the custom ordering defined in the Module Class (using the "compare" method)
		Collections.sort(moduleList, new Module());
		
		// Calls the output method from the "Query" superclass
		super.output();
	}
}
