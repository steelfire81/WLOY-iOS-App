package wloy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;

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
		
		// Start a server
		WLOYBackendServerMainThread serverThread = new WLOYBackendServerMainThread(this);
		serverThread.start();
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
		else if(source == parent.buttonLoadSchedule)
			loadSchedule();
		else if(source == parent.buttonSwitchToSchedule)
			parent.switchToSchedule();
		else if(source == parent.buttonSwitchToStats)
			parent.switchToStats();
	}
	
	/**
	 * load an existing XML schedule file into the schedule panel
	 */
	private void loadSchedule()
	{
		// TODO: Ask to save current schedule
		
		JFileChooser selector = new JFileChooser();
		selector.setCurrentDirectory(null);
		int result = selector.showOpenDialog(parent.frame);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File loadFile = selector.getSelectedFile();
			
			try
			{
				Scanner fileScan = new Scanner(loadFile);
				String xml = "";
				while(fileScan.hasNextLine())
					xml += fileScan.nextLine() + "\n";
				fileScan.close();
				parent.panelScheduleSubpanel.loadScheduleFromXML(xml);
			}
			catch(IOException ioe)
			{
				// TODO: Handle IOException
			}
		}
	}
	
	/**
	 * export the schedule to a file
	 */
	private void saveSchedule()
	{
		JFileChooser selector = new JFileChooser();
		selector.setCurrentDirectory(null);
		int result = selector.showSaveDialog(parent.frame);
		
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File saveFile = selector.getSelectedFile();
			
			try
			{
				PrintWriter fileWriter = new PrintWriter(saveFile);
				fileWriter.println(parent.panelScheduleSubpanel.convertScheduleToXML());
				fileWriter.close();
			}
			catch(IOException ioe)
			{
				// TODO: Handle IOException
			}
		}
	}
	
	/**
	 * add feedback to the feedback table
	 * 
	 * @param feedback newly received feedback
	 */
	public void feedbackReceived(WLOYFeedback feedback)
	{
		parent.tableFeedback.addFeedback(feedback);
	}
	
	/**
	 * add request to the request table
	 * 
	 * @param request newly received request
	 */
	public void requestReceived(WLOYRequest request)
	{
		parent.tableRequests.addRequest(request);
	}
}
