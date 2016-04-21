package wloy;

public class WLOYShow {

	// DATA MEMBERS
	private String name;
	private String description;
	
	// METHODS
	/**
	 * initializes a WLOYShow
	 * @param n name of the show
	 * @param d description of the show
	 */
	public WLOYShow(String n, String d)
	{
		name = n;
		description = d;
	}
	
	/**
	 * returns the name of this show
	 * 
	 * @return the name of this show
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * returns the show description
	 * 
	 * @return the show description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * sets the name of this show
	 * 
	 * @param n updated name of this show
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	/**
	 * sets the description of this show
	 * 
	 * @param d updated description of this show
	 */
	public void setDescription(String d)
	{
		description = d;
	}
}
