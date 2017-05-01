package softeng251.queries;

import java.util.HashSet;
import java.util.Set;

public class Summary extends Query
{
	// A String field for saving the number of dependencies declared in the file
	private int _deps = 0;
	
	// A "Set" field that contains all the names of all the different declared modules that have dependencies
	private Set<String> _srcWithDepsSet = new HashSet<String>();
	
	// A "Set" field that contains all the names of all the different declared modules that don't have have dependencies
	private Set<String> _srcNoDepsSet = new HashSet<String>();
	
	// A "Set" field that contains all the names of all the different modules being depended on, that don't have declared dependencies
	private Set<String> _tgtNotSrcSet = new HashSet<String>();
	
	
	
	
	
	// A constructor that takes in two strings fileID and queryID as inputs and sets the _fileID and _queryID fields to those Strings respectively
	// Creates a Summary object
	public Summary(String fileID, String queryID)
	{
		super._fileID = fileID;
		super._queryID = queryID;
	}
	
	// Default constructor for a Summary object
	public Summary()
	{
	}
	
	
	
	
	
	// A method that counts: 
	// The total number of dependencies
	// The number of dependencies each unique module has
	// The number of unique modules that don't have have dependencies
	// The number of modules being depended on, that don't have declared dependencies
	public void inquire(String QueryCommand)
	{
		// Calls the "inquire" method from the abstract class"Query"
		super.inquire(QueryCommand);
		
		// Counts the number of unique modules declared by putting them into a set
		_srcSet.add(moduleName());
		
		// Adds all the modules being depended on, into a set
		// This set is used to count the number of modules being depended on, that don't have declared dependencies
		_tgtNotSrcSet.add(targetName());
		
		// Checks if the the declared module has a dependency
		// If so:
		// Increase the dependency counter by 1
		// Add the name of the module to the a set containing all the names of modules with dependencies
		if(moduleHasATarget())
		{
			_deps++;
			_srcWithDepsSet.add(moduleName());
		}
		
		// If the module does not have a dependency
		// Add the module to a set containing the names of modules without dependencies
		if(!moduleHasATarget())
		{
			_srcNoDepsSet.add(moduleName());
		}
	}
	
	
	// A method that prints the output of the "Summary" query to the command line
	public void output()
	{
		// Only needs to run once
		// Placed here for runtime efficiency of code, 
		// Could have been placed in 'inquire' but would slow down code unnecessarily by ALOT.
		
		// Puts the elements of _srcSet into an array
		String[] _srcArray = _srcSet.toArray(new String[_srcSet.size()]);
		
		// Loops through all the elements in the new array
		for(int i = 0; i < _srcSet.size(); i++)
		{
			// Checks if the set (containing only modules being depended on) has any modules that have been declared to have dependencies
			// If so, remove it from the set
			if(_tgtNotSrcSet.contains(_srcArray[i]))
			{
				_tgtNotSrcSet.remove(_srcArray[i]);
			}
		}
		
		// Calls the outputStub from the Query superclass
		outputStub();
		
		// println statements print out the relevant information to the console
		
		// Prints out the number of dependencies are present in the data, to the console
		System.out.println("DEPS\t" + _deps);
		
		// Prints out the number of dependencies each unique module has, to the console
		System.out.println("SRCWITHDEPS\t" + _srcWithDepsSet.size());
		
		// Prints out the number of unique modules that don't have have dependencies, to the console
		System.out.println("SRCNODEPS\t" + _srcNoDepsSet.size());
		
		// Prints out the number of modules being depended on, that don't have declared dependencies, to the console
		System.out.println("TGTNOTSRC\t" + _tgtNotSrcSet.size());
	}
}
