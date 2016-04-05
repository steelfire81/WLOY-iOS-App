package wloy;

public class WLOYListener {
	
	// DATA MEMBERS
	private int identifier;
	private int timeConnected;
	
	// METHODS
	/**
	 * initializes a WLOYListener given the listener's unique ID
	 * 
	 * @param id unique identifier
	 */
	public WLOYListener(int id)
	{
		identifier = id;
		timeConnected = 0;
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
	 * updates the time of last contact with this listener 
	 * 
	 * @param lc last time this user was contacted
	 */
	public void updateLastContact(int tc)
	{
		timeConnected = tc;
	}
	
	/**
	 * calculates the time this user has been connected from first/last contact
	 * 
	 * @return number of minutes this user has been (or was) connected
	 */
	public int getTimeConnected()
	{
		return timeConnected;
	}
	
	/**
	 * returns a String representation of this listener
	 * 
	 * @return a csv-storable representation of this listener
	 */
	@Override
	public String toString()
	{
		return identifier + "," + timeConnected;
	}
}
