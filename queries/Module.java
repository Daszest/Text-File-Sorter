package softeng251.queries;

import java.util.Comparator;

public class Module implements Comparable<Module>, Comparator<Module>
{
	// An "integer" (primitive) field representing the amount of times a certain module has met the requirements of a query
	private int _amount;
	
	
	// A String field representing the name of a module
	// For example dependency.A
	private String _name;
	
	
	// A string field representing the type of the module
	// For example a type could include but is not limited to: a class or interface
	private String _type;
	
	
	
	
	
	// Default constructor 
	public Module()
	{	
	}
	
	
	// Constructor that sets the "_type" and "_name" fields to the type and name inputs respectively
	// Also sets the "_amount" field to 0
	public Module(String type, String name)
	{
		_type = type;
		_name = name;
		_amount = 0;
	}
	
	
	// Another constructor but this time it also allows the setting of the "_amount" field
	// in addition to setting the other two fields
	public Module(String type, String name, int amount)
	{
		_type = type;
		_name = name;
		_amount = amount;
	}
	
	
	
	
	
	// Method that increases the "_amount" field by 1
	public void increase()
	{
		_amount++;
	}
	
	
	// Method that returns the "_amount" field
	public int numberOfModules()
	{
		return _amount;
	}
	
	
	// Method that returns the "_type" field
	public String typeOfModule()
	{
		return _type;
	}
	
	
	// Method that returns the "_name" field
	public String nameOfModule()
	{
		return _name;
	}

	
	// Method that determines the "Natural Ordering" of the module object
	// I have chosen this to be "alphabetical according to java.lang.String#compareTo()" for very very secret reasons :)
	public int compareTo(Module mod) 
	{
		return this._name.compareTo(mod._name);
	}
	
	
	// Method that determines an alternate ordering of the module object
	// I have chosen the alternate ordering to be in decreasing order according to the "_amount" field
	// in the case of a tie the order will be "alphabetical according to java.lang.String#compareTo()"
	public int compare(Module mod1, Module mod2) 
	{
		if(mod1._amount == mod2._amount)
		{
			return mod1._name.compareTo(mod2._name);
		}
		
		else
		{
			return mod1._amount > mod2._amount ? -1 : 1;
		}
	}
}
