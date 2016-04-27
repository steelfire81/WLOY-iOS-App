package wloy;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WLOYSchedulePanel extends JPanel {
	
	// CONSTANTS - Error Messages
	private static final String ERR_DAY_FORMAT = "Improperly formatted day";
	private static final String ERR_XML_LOAD = "Could not load from XML data";
	
	// CONSTANTS - Grid Dimensions
	private static final int GRID_ROWS = 25;
	private static final int GRID_COLUMNS = 8;
	
	// CONSTANTS - Labels
	private static final String LABEL_HOUR = "HOUR";
	private static final String LABEL_SUNDAY = "SUNDAY";
	private static final String LABEL_MONDAY = "MONDAY";
	private static final String LABEL_TUESDAY = "TUESDAY";
	private static final String LABEL_WEDNESDAY = "WEDNESDAY";
	private static final String LABEL_THURSDAY = "THURSDAY";
	private static final String LABEL_FRIDAY = "FRIDAY";
	private static final String LABEL_SATURDAY = "SATURDAY";
	private static final String LABEL_AM = "A.M.";
	private static final String LABEL_PM = "P.M.";
	
	// CONSTANTS - XML
	private static final String XML_DAY = "day";
	private static final String[] XML_DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private static final String XML_DESCRIPTION = "description";
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private static final String XML_HOUR = "hour";
	private static final String XML_MINUTE = "minute";
	private static final String XML_NAME = "name";
	private static final String XML_ROOT = "root";
	private static final String XML_SHOW = "show";
	
	// DATA MEMBERS
	private JPanel[][] panelGrid;
	private JTextField[][] fieldGrid;
	
	// METHODS
	public WLOYSchedulePanel()
	{
		super(new GridLayout(GRID_ROWS, GRID_COLUMNS));
		
		// Initialize panel grid
		panelGrid = new JPanel[GRID_ROWS][GRID_COLUMNS];
		for(int row = 0; row < panelGrid.length; row++)
			for(int col = 0; col < panelGrid[row].length; col++)
			{
				panelGrid[row][col] = new JPanel(new GridLayout(1, 1));
				add(panelGrid[row][col]);
			}
		
		// Add top level labels
		JTextField labelHour = new JTextField(LABEL_HOUR);
		labelHour.setEditable(false);
		JTextField labelSunday = new JTextField(LABEL_SUNDAY);
		labelSunday.setEditable(false);
		JTextField labelMonday = new JTextField(LABEL_MONDAY);
		labelMonday.setEditable(false);
		JTextField labelTuesday = new JTextField(LABEL_TUESDAY);
		labelTuesday.setEditable(false);
		JTextField labelWednesday = new JTextField(LABEL_WEDNESDAY);
		labelWednesday.setEditable(false);
		JTextField labelThursday = new JTextField(LABEL_THURSDAY);
		labelThursday.setEditable(false);
		JTextField labelFriday = new JTextField(LABEL_FRIDAY);
		labelFriday.setEditable(false);
		JTextField labelSaturday = new JTextField(LABEL_SATURDAY);
		labelSaturday.setEditable(false);
		add(labelHour, 0, 0);
		add(labelSunday, 0, 1);
		add(labelMonday, 0, 2);
		add(labelTuesday, 0, 3);
		add(labelWednesday, 0, 4);
		add(labelThursday, 0, 5);
		add(labelFriday, 0, 6);
		add(labelSaturday, 0, 7);
		
		// Add hour labels on first column
		int row = 1;
		for(int ampm = 0; ampm < 2; ampm++)
			for(int hour = 0; hour < 12; hour++)
			{
				int printedHour = hour;
				if(printedHour == 0)
					printedHour = 12;
				
				String ampmText;
				if(ampm == 0)
					ampmText = LABEL_AM;
				else
					ampmText = LABEL_PM;
				
				JTextField labelHourRow = new JTextField(printedHour + " " + ampmText);
				labelHourRow.setEditable(false);
				add(labelHourRow, row, 0);
				row++;
			}
		
		// Add textfields for each timeslot
		fieldGrid = new JTextField[GRID_ROWS - 1][GRID_COLUMNS - 1];
		for(row = 1; row < GRID_ROWS; row++)
			for(int col = 1; col < GRID_COLUMNS; col++)
			{
				fieldGrid[row - 1][col - 1] = new JTextField();
				add(fieldGrid[row - 1][col - 1], row, col);
			}
	}
	
	/**
	 * add a component to a particular spot on the grid
	 * 
	 * @param component component to be added
	 * @param row row in the grid to add the component
	 * @param col column in the grid to add the component
	 */
	public void add(Component component, int row, int col)
	{
		panelGrid[row][col].add(component);
	}
	
	/**
	 * converts the schedule stored in this panel to XML
	 * 
	 * @return an XML String of the schedule for file export
	 */
	public String convertScheduleToXML()
	{
		String xml = XML_HEADER + "\n";
		xml += xmlElementOpen(XML_ROOT) + "\n";
		
		// Add items from each day column if item is not empty
		for(int col = 0; col < XML_DAYS.length; col++)
		{
			String day = XML_DAYS[col];
			for(int row = 0; row < fieldGrid.length; row++)
				if((fieldGrid[row][col].getText() != null) && !fieldGrid[row][col].getText().equals(""))
				{
					String item = "\t" + xmlElementOpen(XML_SHOW) + "\n";
					item += formatXMLRow(XML_NAME, fieldGrid[row][col].getText(), 2)+ "\n";
					item += formatXMLRow(XML_DAY, day, 2) + "\n";
					item += formatXMLRow(XML_HOUR, Integer.toString(row), 2) + "\n";
					item += formatXMLRow(XML_MINUTE, "0", 2) + "\n"; // For now, minute is always 0
					item += formatXMLRow(XML_DESCRIPTION, "", 2) + "\n"; // For now, descriptions are not available
					item += "\t" + xmlElementClose(XML_SHOW) + "\n";
					xml += item;
				}
		}
		
		xml += xmlElementClose(XML_ROOT) + "\n";
		return xml;
	}
	
	/**
	 * formats an XML row
	 * 
	 * @param elementName name of the element
	 * @param elementValue value of the element
	 * @param numTabs tabs preceding the row
	 * @return a formatted XML String for one element
	 */
	private String formatXMLRow(String elementName, String elementValue, int numTabs)
	{
		String row = "";
		for(int i = 0; i < numTabs; i++)
			row += "\t";
		row += xmlElementOpen(elementName) + elementValue + xmlElementClose(elementName);
		return row;
	}
	
	/**
	 * formats the opening of an XML element
	 * 
	 * @param elementName name of the element
	 * @return a formatted XML element opening as a String
	 */
	private String xmlElementOpen(String elementName)
	{
		return "<" + elementName + ">";
	}
	
	/**
	 * formats the close of an XML element
	 * 
	 * @param elementName name of the element
	 * @return a formatted XML element closing as a String
	 */
	private String xmlElementClose(String elementName)
	{
		return "</" + elementName + ">";
	}
	
	/**
	 * opens a schedule from an XML file (as a String)
	 * 
	 * @param xml String containing an XML file's contents
	 */
	public void loadScheduleFromXML(String xml) throws IOException
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
			Document doc = builder.parse(input);
			
			NodeList showList = doc.getElementsByTagName(XML_SHOW);
			for(int i = 0; i < showList.getLength(); i++)
			{
				Element e = (Element) showList.item(i);
				String name = e.getElementsByTagName(XML_NAME).item(0).getTextContent();
				String day = e.getElementsByTagName(XML_DAY).item(0).getTextContent();
				int hour = Integer.parseInt(e.getElementsByTagName(XML_HOUR).item(0).getTextContent());
				// As of right now, minute and description do nothing
				int minute = Integer.parseInt(e.getElementsByTagName(XML_MINUTE).item(0).getTextContent());
				String description = e.getElementsByTagName(XML_DESCRIPTION).item(0).getTextContent();
				
				// Add this show to the grid
				int dayNumber = -1;
				for(int j = 0; j < XML_DAYS.length; j++)
					if(day.equals(XML_DAYS[j]))
						dayNumber = j;
				if(dayNumber == -1) // Ensure day is properly formatted
					throw new IOException(ERR_DAY_FORMAT);
				
				fieldGrid[hour][dayNumber].setText(name);
			}
		}
		catch(NumberFormatException nfe)
		{
			throw new IOException(ERR_XML_LOAD);
		}
		catch(IOException ioe)
		{
			throw ioe;
		}
		catch(Exception e) // Something went wrong with the parser
		{
			throw new IOException(ERR_XML_LOAD);
		}
	}
}
