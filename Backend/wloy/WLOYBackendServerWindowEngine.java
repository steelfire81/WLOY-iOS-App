package wloy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class WLOYBackendServerWindowEngine implements ActionListener {

	// DATA MEMBERS
	private WLOYBackendServerWindow parent;
	
	// METHODS
	/**
	 * initialize a WLOYBackendServerWindowEngine with a parent window
	 * 
	 * @param p the window to which this engine belongs
	 */
	public WLOYBackendServerWindowEngine(WLOYBackendServerWindow p)
	{
		parent = p;
	}
	
	/**
	 * handle an action from the parent window
	 * 
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if(source == parent.buttonSaveSchedule)
			saveSchedule();
	}
	
	/**
	 * export the schedule to a file
	 */
	private void saveSchedule()
	{
		// DEBUG - print for now
		System.out.println(parent.panelScheduleSubpanel.convertScheduleToXML());
	}
}
