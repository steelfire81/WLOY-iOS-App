package wloy;

public class WLOYFeedback {

	// CONSTANTS
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
