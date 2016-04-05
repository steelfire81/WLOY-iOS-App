package wloy;

import java.util.Date;

public class WLOYListener {

	// DATA MEMBERS
	private int identifier;
	private int minutesListened;
	private Date lastContact;
	
	// METHODS
	/**
	 * initializes a WLOYListener given the listener's unique ID
	 * 
	 * @param id unique identifier
	 */
	public WLOYListener(int id)
	{
		identifier = id;
		minutesListened = 0;
	}
	
	/**
	 * returns this listener's unique identifier
	 * 
	 * @return this listener's unique identifier
	 */
	public int getIdentifier()
	{
		return identifier;
	}
	
	/**
	 * returns the number of minutes this listener has been / was connected
	 * 
	 * @return number of minutes this listeners has been / was connected
	 */
	public int getMinutesListened()
	{
		return minutesListened;
	}
	
	/**
	 * returns a string representation of this listener
	 * 
	 * @return a string representation of this listener
	 */
	@Override
	public String toString()
	{
		return identifier + "," + minutesListened;
	}
}
