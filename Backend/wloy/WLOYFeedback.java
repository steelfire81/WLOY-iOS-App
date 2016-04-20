package wloy;

public class WLOYFeedback {

	// CONSTANTS
	/**
	 * first row to be exported in a feedback .csv file
	 */
	public static final String[] CSV_COLUMN_HEADERS = {"Positive/Negative", "Song", "Artist", "Show", "DJ"};
	private static final String FEEDBACK_POSITIVE = "positive";
	private static final String FEEDBACK_NEGATIVE = "negative";
	
	// DATA MEMBERS
	private boolean positive;
	private String songTitle;
	private String artist;
	private String show;
	private String dj;
	
	// METHODS
	/**
	 * initializes a WLOYFeedback
	 * 
	 * @param p <b>true</b> if feedback is positive, <b>false</b> if negative
	 * @param t title of song given feedback
	 * @param a artist of song given feedback
	 * @param s name of show given feedback
	 * @param d name of dj given feedback
	 */
	public WLOYFeedback(boolean p, String t, String a, String s, String d)
	{
		positive = p;
		songTitle = t;
		artist = a;
		show = s;
		dj = d;
	}
	
	/**
	 * returns the title of the song this feedback is for
	 * 
	 * @return the title of the song this feedback is for
	 */
	public String getTitle()
	{
		return songTitle;
	}
	
	/**
	 * returns the artist of the song this feedback is for
	 * 
	 * @return the artist of the song this feedback is for
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * returns the show this feedback is for
	 * 
	 * @return the show this feedback is for
	 */
	public String getShow()
	{
		return show;
	}
	
	/**
	 * returns the DJ this feedback is for
	 * 
	 * @return the DJ this feedback is for
	 */
	public String getDJ()
	{
		return dj;
	}
	
	/**
	 * returns whether or not this feedback is positive
	 * 
	 * @return <b>true</b> if feedback is positive, <b>false</b> if negative
	 */
	public boolean isPositive()
	{
		return positive;
	}
	
	/**
	 * returns a string for this feedback that can be stored in a .csv file
	 * 
	 * @return a string representation of this feedback
	 */
	@Override
	public String toString()
	{
		String feedback;
		if(positive)
			feedback = FEEDBACK_POSITIVE;
		else
			feedback = FEEDBACK_NEGATIVE;
		
		return feedback + "," + songTitle + "," + artist + "," + show + "," + dj;
	}
}
