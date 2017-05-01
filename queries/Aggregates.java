package softeng251.queries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Aggregates extends Query
{
	// A "Set" field that is used for keeping a record of each unique source module and target dependency combination
	private Set<String> _aggregateSet = new HashSet<String>();
	
	
	
	
	
	// A constructor that takes in two strings fileID and queryID as inputs and sets the _fileID and _queryID fields to those Strings respectively
	// Creates a Aggregates object
	public Aggregates(String fileID, String queryID)
	{
		super._fileID = fileID;
		super._queryID = queryID;
	}
	
	
	// Default constructor for an Aggregates object
	public Aggregates()
	{
	}
	
	
	
	
	
	// A method that does all the heavy lifting for the Aggregates query
	// The Aggregates query is supposed to list all source modules declared in the text file along with the number of other modules "used by" the source module in question
	// Module A is "used by" Module B means: 
	// Module A either invokes a method or accesses a field belonging to module B
	// The order of the list of source modules is the natural ordering of "Module" objects (using "compareTo" method in class "Module")
	// This method does all the behind scenes work for Aggregates, that is it does everything except for printing the information to the console and the sorting
	// Those two actions are done in the "output" method
	public void inquire(String QueryCommand)
	{
		// Calls the inquire method from the abstract class "Query"
		super.inquire(QueryCommand);
		
		// Creates a string that is a concatenation of the name of the declared module and its target dependency
		String tgtConcat = moduleName() + targetName();
		
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
		
		// Checks if the module has already been added to the set of Source Modules
		// Also checks if:
		// 1:	The source module has a target dependency 
		// 2:	The source module depends on itself
		// 3:	The source module uses the target dependency as a type of field
		// The 3rd condition is checked in 2 ways
		// First by checking if the length of the Uses part of the data is at least 5 characters long
		// If the "Uses" part of the data begins with "Field", then the source module uses the target dependency as a type of field
		// If the above conditions are met then we increase the counter in the module object
		// This is done by getting the index of the module from the tree 
		// and using it to find the appropriate module in the ArrayList "moduleList"
		// The increase method is then invoked on the object
		if(!_srcSet.add(moduleName()) && moduleHasATarget())
		{
			if(!moduleDependsOnItself() && !moduleUseDetails().equals("") && moduleCategory().length() >= 5)
			{
				if(moduleCategory().substring(0, 5).equals("Field"))
				{
					if(_aggregateSet.add(tgtConcat))
					{
						int tempIndex = indexMap.get(moduleName());
						moduleList.get(tempIndex).increase();
					}
				}
			}
		}
	}
	
	
	// A method that prints the output of the "Aggregates" query to the command line
	public void output()
	{
		// Sorts "moduleList" according to the natural ordering defined in the Module Class (using the "compareTo" method)
		Collections.sort(moduleList);
		
		// Calls the output method from the "Query" superclass
		super.output();
	}
}
