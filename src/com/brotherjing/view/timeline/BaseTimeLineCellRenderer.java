package com.brotherjing.view.timeline;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-07.
 */
public class BaseTimeLineCellRenderer extends BaseTimeLineGraph implements TableCellRenderer {

    public BaseTimeLineCellRenderer() {
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
        BaseTimeLine baseTimeLine = (BaseTimeLine)value;
        setFromDate(baseTimeLine.getStart());
        setToDate(baseTimeLine.getEnd());
        setTimeLine(baseTimeLine.getRecordList());
        return this;
    }
}
