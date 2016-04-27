package wloy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
				
			}
		}
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
