package softeng251.queries;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FanIn extends Query
{
	// A "Set" field that is used for keeping a record of each unique target dependency along with every unique module that depends that target dependency
	private Set<String> _fanInSet = new HashSet<String>();
	
	
	// A "Map" field that stores the name of a target dependency along with the number of unique modules that depend on it
	private Map<String, Integer> tgtOccurMap = new HashMap<String, Integer>();
	
	
	// A "Map" field that stores the name of a source module along with that module's "type"
	// An example of a type could be: Class or Interface
	private Map<String, String> moduleTypeMap = new HashMap<String, String>();
	
	
	// A "Set" field of Strings that is used for storing the names of unique target dependencies
	private Set<String> _tgtSet = new HashSet<String>();
	
	
	
	
	
	// A constructor that takes in two strings fileID and queryID as inputs and sets the _fileID and _queryID fields to those Strings respectively
	// Creates a FanIn object
	public FanIn(String fileID, String queryID)
	{
		super._fileID = fileID;
		super._queryID = queryID;
	}
	
	
	// Default constructor for a FanIn object
	public FanIn()
	{
	}
	
	
	
	
	
	// A method that does all the heavy lifting for the FanIn query
	// The FanIn query is supposed to list all source modules declared in the text file along with the number of other source modules that depend on the source module in question
	// The order of the list of source modules is the natural ordering of "Module" objects (using "compareTo" method in class "Module")
	// This method does all the behind scenes work for FanIn, that is it does everything except for printing the information to the console and the sorting
	// Those two actions are done in the "output" method
	public void inquire(String QueryCommand)
	{
		// Calls the inquire method from the abstract class "Query"
		super.inquire(QueryCommand);
		
		// Creates a string that is a concatenation of the name of the declared module and its target dependency
		String concatenate = moduleName() + targetName();
		
		// Tries to add the name of the module to the set of source modules
		// If successful (only fails if the module is already present):
		// Puts the same name along with it's "type" into "moduleTypeMap" as the "Key" and "Value" values in a map respectively (both values are Strings)
		// see comment for moduleTypeMap for an explanation for "type"
		if(_srcSet.add(moduleName()))
		{
			moduleTypeMap.put(moduleName(), kindOfModule());
		}
		
		// Checks if the module depends on itself
		if(!moduleDependsOnItself())
		{
			// Tries to add the name of the target dependency to "_tgtSet"
			// If successful, the target dependency along with the integer "1" is put into the Map "tgtOccurMap"
			// Also the concatenation of the source module name and the target dependency name is put into _fanInSet
			// This is to keep a record of which source modules have already been seen to depend on the target dependency in question
			if(_tgtSet.add(targetName()))
			{
				tgtOccurMap.put(targetName(), 1);
				_fanInSet.add(concatenate);	
			}
			
			// If the target dependency has already been added to "_tgtSet" and the module has a target and the concatenation hasn't been added to 	_fanInSet
			if(!_tgtSet.add(targetName()) && moduleHasATarget())
			{
				// If the concatenation of source module and target dependency hasn't been added to _fanInSet yet
				if(_fanInSet.add(concatenate))
				{
					// Get the integer associated with the target dependency name and increase it by 1
					// Then put that integer back into the map as the new "Value" associated with the target dependency name ("Key")
					int occurence = tgtOccurMap.get(targetName()) + 1;
					tgtOccurMap.replace(targetName(), occurence);
				}
			}
		}
	}
	
	
	// A method that prints the output of the "FanIn" query to the command line
	public void output()
	{
		// Only needs to run once when every line of the scrubbed data has been processed
		
		// Puts the elements of _srcSet into an array
		String[] srcSetArray = _srcSet.toArray(new String[_srcSet.size()]);
		
		// Loops through all the elements in the new array
		for(int i = 0; i < _srcSet.size(); i++)
		{
			// Checks if there is a "Value" associated with each entry in srcSetArray
			// If there is a value, then make a Module object with the following as inputs:
			// 2nd input:	The source module name in the ith value in srcSetArray
			// 1st input:	The "type" associated with the source module name
			// 3rd input:	The "Value" associated with the source module name (number of other source modules that depend on the source module in question)
			if(tgtOccurMap.get(srcSetArray[i]) != null)
			{
				moduleList.add(new Module(moduleTypeMap.get(srcSetArray[i]), srcSetArray[i], tgtOccurMap.get(srcSetArray[i])));
			}
			
			// If there is no value, then make a Module object with:
			// 2nd input:	The source module name in the ith value in srcSetArray
			// 1st input:	The "type" associated with the source module name
			// The _amount field of the Module object is set to a default value of 0
			if(tgtOccurMap.get(srcSetArray[i]) == null)
			{
				moduleList.add(new Module(moduleTypeMap.get(srcSetArray[i]), srcSetArray[i]));
			}
		}
		
		// Sorts "moduleList" according to the natural ordering defined in the Module Class (using the "compareTo" method)
		Collections.sort(moduleList);
		
		// Calls the output method from the "Query" superclass
		super.output();
	}
}
