package wloy;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class WLOYRequestTable extends JTable {

	/**
	 *	handle clicks in the request table
	 */
	private class WLOYRequestTableMouseAdapter extends MouseAdapter {
		
		// DATA MEMBERS
		private WLOYRequestTable table;
		
		// METHODS
		/**
		 * initializes a WLOYRequestTableMouseAdapter
		 * 
		 * @param t table to which this adapter belongs
		 */
		private WLOYRequestTableMouseAdapter(WLOYRequestTable t)
		{
			table = t;
		}
		
		/**
		 * signifies the mouse has clicked on the table
		 * 
		 * @param e mouse click event
		 */
		@Override
		public void mouseClicked(MouseEvent e)
		{
			int row = table.rowAtPoint(e.getPoint());
			int col = table.columnAtPoint(e.getPoint());
			
			if(col == WLOYRequestTable.COLUMN_DELETE)
				table.deleteRequestAt(row);
		}
	}
	
	
	/**
	 * handles data stored in the table; only change is that cells
	 * are not editable
	 */
	private class WLOYRequestTableModel extends DefaultTableModel {
		
		// METHODS
		/**
		 * initializes a WLOYRequestTableModel
		 */
		public WLOYRequestTableModel()
		{
			super(new Object[0][0], WLOYRequestTable.REQUEST_COLUMN_NAMES);
		}
		
		/**
		 * says whether or not a cell is editable
		 * 
		 * @return <b>false</b> always
		 */
		@Override
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	}
	
	
	/**
	 * handles rendering of the table's delete buttons
	 */
	private class WLOYDeleteCellRenderer extends DefaultTableCellRenderer {
		
		/**
		 * gets the component at a given location and renders it as a button
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
		{
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			JButton button = new JButton(label.getText());
			return button;
		}
	}
	
	
	// CONSTANTS
	private static final String[] REQUEST_COLUMN_NAMES = {"TITLE", "ARTIST", "DELETE"};
	private static final int COLUMN_DELETE = 2;
	private static final String DELETE_CELL_TEXT = "DELETE";
	
	// DATA MEMBERS
	private DefaultTableModel model;
	
	// METHODS
	/**
	 * initializes a request table
	 */
	public WLOYRequestTable()
	{
		addMouseListener(new WLOYRequestTableMouseAdapter(this));
		model = new WLOYRequestTableModel();
		setModel(model);
		getColumnModel().getColumn(COLUMN_DELETE).setCellRenderer(new WLOYDeleteCellRenderer());
	}
	
	/**
	 * add a request to the table
	 * 
	 * @param request request to be added
	 */
	public void addRequest(WLOYRequest request)
	{
		Object[] newRow = {request.getTitle(), request.getArtist(), DELETE_CELL_TEXT};
		model.addRow(newRow);
	}
	
	/**
	 * delete the request at a given row
	 * 
	 * @param row row to be deleted
	 */
	public void deleteRequestAt(int row)
	{
		// Ensure row exists within table
		if((row >= 0) && (row < model.getRowCount()))
		{
			model.removeRow(row);
		}
	}
}
