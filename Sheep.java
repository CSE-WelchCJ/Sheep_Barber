/*
 * Written by Chris Welch CSCE 146-007
 */

// Defines a sheep object
public class Sheep implements Comparable<Sheep>{

	// Declares the required components of a sheep object
	protected String name;
	protected int shearingTime;
	protected int arrivalTime;
	protected int shearingTimeLeft;
	// Declares constants for default values
	public static final String DEF_NAME ="none";
	public static final int DEF_TIME = 100;
	
	// Default constructor for sheep object
	public Sheep()
	{
		this.name = DEF_NAME;
		this.shearingTime = DEF_TIME;
		this.arrivalTime = DEF_TIME;
		this.shearingTimeLeft = DEF_TIME;
	}
	
	// Parameterized constructor for sheep object
	public Sheep(String aName, int aShearingTime, int aArrivalTime)
	{
		setName(aName);
		// setShearingTime will also set shearingTimeLeft since both are equal at start
		setShearingTime(aShearingTime);
		setArrivalTime(aArrivalTime);
	}
	
	// Getters for instance variables
	public String getName()
	{
		return this.name;
	}
	
	public int getShearingTime()
	{
		return this.shearingTime;
	}
	
	public int getArrivalTime()
	{
		return this.arrivalTime;
	}
	
	public int getShearingTimeLeft()
	{
		return this.shearingTimeLeft;
	}
	
	// Mutators for instance variables, checks the value for appropriateness
	public void setName(String aName)
	{
		if(aName != null)
		{
			this.name = aName;
		}
		else
		{
			this.name = DEF_NAME;
		}
	}
	
	// Sets the shearingTimeLeft as well because they are equal when first assigned
	public void setShearingTime(int aShearingTime)
	{
		if(aShearingTime >= 0)
		{
			this.shearingTime = aShearingTime;
			this.shearingTimeLeft = aShearingTime;
		}
		else
		{
			this.shearingTime = DEF_TIME;
			this.shearingTimeLeft = DEF_TIME;
		}
	}
	
	public void setArrivalTime(int aArrivalTime)
	{
		if(aArrivalTime >= 0)
		{
			this.arrivalTime = aArrivalTime;
		}
		else
		{
			this.arrivalTime = DEF_TIME;
		}
	}
	
	// Method to reduce shearingTimeLeft when a unit of time passes
	public void shearForOneMinute()
	{
		this.shearingTimeLeft = this.shearingTimeLeft - 1;
	}
	
	// Method to recognize when shearingTimeLeft reaches zero and current shear job is complete
	public boolean isDone()
	{
		if(this.shearingTimeLeft == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Method to print object details as required by assignment
	public String toString()
	{
		return "Name: "+this.name+", Shear Time: "+this.shearingTime+", Arrival Time: "+this.arrivalTime;
	}
	
	/*
	 * required method for implementing Comparable.  Compares first by shearingTime and then by alphabetical order.
	 * Alphabetical order checks use the String compareTo() method
	 */
	public int compareTo(Sheep s)
	{
		if(this.shearingTime > s.getShearingTime())
		{
			return 1;
		}
		else if(this.shearingTime < s.getShearingTime())
		{
			return -1;
		}
		else
		{
			if(this.name.compareTo(s.getName()) > 0)
			{
				return 1;
			}
			else if(this.name.compareTo(s.getName()) < 0)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
	}	
} // Close class
