import javax.swing.table.AbstractTableModel;

public class CustomTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	byte[][] data;

	public CustomTable(byte[][] data) {
		super();
		this.data = data;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getByteAsHex(data[rowIndex][columnIndex]);
	}

	@Override
	public int getRowCount() {
		return 256;
	}

	@Override
	public int getColumnCount() {
		return 256;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			String d = (String) aValue;
			data[rowIndex][columnIndex] = (byte) Integer.parseInt(d);
		} 
		catch (Exception e) {
		}
	}

	@Override
	public String getColumnName(int column) {
		return getByteAsHex((byte) column);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public static String getByteAsHex(Byte b) {
		String a = Integer.toHexString(Byte.toUnsignedInt(b)).toUpperCase();
		if (a.length() == 1) {
			a = "0" + a;
		}
		return a;
	}
}
