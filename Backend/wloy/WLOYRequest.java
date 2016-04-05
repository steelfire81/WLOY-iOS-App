package wloy;

public class WLOYRequest {

	// DATA MEMBERS
	private String songTitle;
	private String artist;
	
	// METHODS
	/**
	 * initializes a new WLOYRequest
	 * 
	 * @param t requested song title
	 * @param a requested song artist
	 */
	public WLOYRequest(String t, String a)
	{
		songTitle = t;
		artist = a;
	}
	
	/**
	 * provides a String representation of this WLOYRequest
	 * 
	 * @return a String representation of this request
	 */
	@Override
	public String toString()
	{
		return artist + " - " + songTitle;
	}
}
