package com.brotherjing.view.timeline;

import jp.co.worksap.intern.entities.room.RoomTimeLine;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by Brotherjing on 2015-12-07.
 */
public class TimeLineCellRenderer extends TimeLineGraph implements TableCellRenderer {

    public TimeLineCellRenderer(){
        super(null,null,null);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        }
        else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        RoomTimeLine roomTimeLine = (RoomTimeLine)value;
        setFromDate(roomTimeLine.getStart());
        setToDate(roomTimeLine.getEnd());
        setTimeLine(roomTimeLine);
        return this;
        //return new TimeLineGraph(roomTimeLine.getStart(),roomTimeLine.getEnd(),roomTimeLine);
    }
}
