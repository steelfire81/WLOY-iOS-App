package fakeclient;

public class Timer extends Thread {

	// CONSTANTS
	private static final int TIME_INTERVAL = 60000;
	private static final int SEND_INTERVAL = 5;
	
	// DATA MEMBERS
	private FakeClientEngine parent;
	private int time;
	
	// METHODS
	/**
	 * initialize a timer at time 0
	 */
	public Timer(FakeClientEngine p)
	{
		parent = p;
		time = 0;
	}
	
	/**
	 * ticks the timer every minute connected
	 */
	@Override
	public void run()
	{
		boolean running = true;
		
		try
		{
			while(running)
			{
				sleep(TIME_INTERVAL);
				time++;
				
				if(time % SEND_INTERVAL == 0)
					parent.sendConnectionMessage();
			}
		}
		catch(InterruptedException ie)
		{
			running = false;
		}
	}
	
	/**
	 * gets the time this timer has been running in minutes
	 * 
	 * @return time this timer has been running in minutes
	 */
	public int getTime()
	{
		return time;
	}
}
