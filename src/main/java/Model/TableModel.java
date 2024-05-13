package main.java.Model;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

    public TableModel(Object[] columnNames, int rowCount){
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}
