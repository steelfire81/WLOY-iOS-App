package fakeclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FakeClientEngine implements ActionListener {

	// CONSTANTS
	private static final String FEEDBACK_POSITIVE = "POSITIVE";
	private static final String FEEDBACK_NEGATIVE = "NEGATIVE";
	private static final String HEADER_CONNECTION = "CONNECTION";
	private static final String HEADER_FEEDBACK = "FEEDBACK";
	private static final String HEADER_REQUEST = "REQUEST";
	private static final int ID = 123456789;
	
	// DATA MEMBERS
	private FakeClientWindow parent;
	private Timer timer;
	
	// METHODS
	public FakeClientEngine(FakeClientWindow p)
	{
		parent = p;
		timer = new Timer(this);
	}
	
	/**
	 * initialize the window and engine
	 */
	public void initialize()
	{
		parent.buttonNegative.setEnabled(false);
		parent.buttonPositive.setEnabled(false);
		parent.buttonRequest.setEnabled(false);
	}

	/**
	 * handle button presses
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == parent.buttonConnect)
			connect();
		else if(source == parent.buttonPositive)
			sendFeedback(true);
		else if(source == parent.buttonNegative)
			sendFeedback(false);
		else if(source == parent.buttonRequest)
			sendRequest();
	}
	
	public void connect()
	{
		try
		{
			String hostname = parent.fieldIP.getText();
			int port = Integer.parseInt(parent.fieldPort.getText());
			
			// Verify connection
			Socket socket = new Socket(hostname, port);
			socket.close();
			
			// Send connection message
			timer.start();
			sendConnectionMessage();
			
			parent.fieldIP.setEditable(false);
			parent.fieldPort.setEditable(false);
			parent.buttonConnect.setEnabled(false);
			parent.buttonPositive.setEnabled(true);
			parent.buttonNegative.setEnabled(true);
			parent.buttonRequest.setEnabled(true);
		}
		catch(NumberFormatException nfe)
		{
			System.err.println("ERROR: Invalid port");
			return;
		}
		catch(IOException ioe)
		{
			System.err.println("ERROR: Could not connect");
			return;
		}
	}
	
	public void sendConnectionMessage()
	{
		String message = HEADER_CONNECTION + "\n";
		message += ID + "\n";
		message += timer.getTime();
		sendMessage(message);	
	}
	
	private void sendFeedback(boolean positive)
	{
		String message = HEADER_FEEDBACK + "\n";
		message += ID + "\n";
		message += timer.getTime() + "\n";
		
		if(positive)
			message += FEEDBACK_POSITIVE + "\n";
		else
			message += FEEDBACK_NEGATIVE + "\n";
		
		message += parent.fieldFeedbackTitle.getText() + "\n";
		message += parent.fieldFeedbackArtist.getText() + "\n";
		message += parent.fieldFeedbackShow.getText() + "\n";
		message += parent.fieldFeedbackDJ.getText();
		sendMessage(message);
	}
	
	private void sendRequest()
	{	
		String message = HEADER_REQUEST + "\n";
		message += ID + "\n";
		message += timer.getTime() + "\n";
		message += parent.fieldRequestTitle.getText() + "\n";
		message += parent.fieldRequestArtist.getText();
		sendMessage(message);
	}
	
	private void sendMessage(String message)
	{
		// Debug: print message
		System.out.println();
		System.out.println("SENDING:");
		System.out.println(message);
		System.out.println();
		
		try
		{
			String hostname = parent.fieldIP.getText();
			int port = Integer.parseInt(parent.fieldPort.getText());
			Socket socket = new Socket(hostname, port);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(message);
			output.flush();
			socket.close();
		}
		catch(IOException ioe)
		{
			System.err.println("Could not send following message:");
			System.err.println(message);
			System.err.println();
		}
	}
}
