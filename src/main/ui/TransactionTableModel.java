package ui;

import javax.swing.table.DefaultTableModel;

// JTable model for TransactionPanel
public class TransactionTableModel extends DefaultTableModel {

    private static String[] columnNames = {"Type", "Amount", "Date"};
    private static Object[][] rowData;

    public TransactionTableModel(Object[][] rowData) {
        super(rowData, columnNames);
        this.rowData = rowData;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    // No cells editable
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        rowData[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }


}
