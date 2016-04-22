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
		
	}
}
