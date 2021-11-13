package domain;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class BezeroAdapter extends AbstractTableModel{

	private String[] columnNames = {"Event","Question","EventDate","Bet"};
	private Vector<Apustua> apustuak;
	
	public BezeroAdapter(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	@Override
	public int getRowCount() {
		if(this.apustuak == null) {
			return 0;
		}
		return this.apustuak.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(columnIndex == 0) {
			return this.apustuak.get(rowIndex).getPronostikoak().get(rowIndex).getQ().getEvent();
		}
		else if(columnIndex == 1) {
			return this.apustuak.get(rowIndex).getPronostikoak().get(rowIndex).getQ().getQuestion();
		}
		else if(columnIndex == 2) {
			return this.apustuak.get(rowIndex).getPronostikoak().get(rowIndex).getQ().getEvent().getEventDate();
		}
		return this.apustuak.get(rowIndex).getApustuDirua();
	}
	
	public String getColumnName(int col) {
		return this.columnNames[col];
	}

}
