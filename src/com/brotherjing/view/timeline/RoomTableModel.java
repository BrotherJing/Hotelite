package com.brotherjing.view.timeline;

import jp.co.worksap.intern.entities.room.RoomTimeLine;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Brotherjing on 2015-12-07.
 */
public class RoomTableModel extends DefaultTableModel {

    public RoomTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==2)return RoomTimeLine.class;
        return super.getColumnClass(columnIndex);
    }
}
