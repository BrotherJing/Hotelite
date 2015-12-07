package com.brotherjing.view.pages;

import com.brotherjing.controller.StaffController;
import com.brotherjing.view.Labels;
import com.brotherjing.view.timeline.*;
import com.brotherjing.view.dialog.ShiftDialog;
import jp.co.worksap.intern.entities.staff.ShiftRecord;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.util.Utilities;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class ShiftPage extends JPanel {


    private JFrame parent;
    private JTable jTable;

    private JButton b1 = new JButton(Labels.SHIFT), b2 = new JButton(Labels.SEARCH);
    private JTextField fromDate = new JTextField(20), toDate = new JTextField(20);
    private BaseTimeLineGraph timeLineGraph;

    private StaffController staffController;

    private String[] headers = {"STAFF NAME", "STATE", "TIMELINE"};

    private class RowSelectListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() || jTable.getSelectionModel().getLeadSelectionIndex() < 0) {
                return;
            }

            staffController.setSelected(jTable.getSelectionModel().getLeadSelectionIndex());
            timeLineGraph.setTimeLine(TimeLineRecord.from(staffController.getSelectedItem().getShiftRecordList()));
        }
    }

    public ShiftPage(JFrame parent) {

        this.parent = parent;

        initView();

        initData();

        fillData();

    }

    private void fillData(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date from = calendar.getTime();
        fromDate.setText(Utilities.formatDateTime(from));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        toDate.setText(Utilities.formatDateTime(calendar.getTime()));
        timeLineGraph.setFromDate(from);
        timeLineGraph.setToDate(calendar.getTime());

        refreshList();
    }

    private void refreshList() {
        staffController.range(Utilities.parseDateTimeStr(fromDate.getText()), Utilities.parseDateTimeStr(toDate.getText()));
        DefaultTableModel model = new DefaultTableModel(null, headers){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex==2)return BaseTimeLine.class;
                return super.getColumnClass(columnIndex);
            }
        };
        for (StaffDTO staff:staffController.getStaffList()) {
            StringBuilder element = new StringBuilder();
            model.addRow(new Object[]{staff.getName(), staff.getStaffState(), new BaseTimeLine(TimeLineRecord.from(staff.getShiftRecordList()),Utilities.parseDateTimeStr(fromDate.getText()), Utilities.parseDateTimeStr(toDate.getText()))});
        }
        jTable.setModel(model);
        jTable.setDefaultRenderer(BaseTimeLine.class,new BaseTimeLineCellRenderer());
        initColumnSize(jTable);
        jTable.repaint();
    }

    private void initColumnSize(JTable table){
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);
    }

    private void initData() {
        staffController = new StaffController();
    }

    private void initView() {
        b1.addActionListener(listener);
        b2.addActionListener(listener);
        setLayout(new BorderLayout());
        //add(txt);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(fromDate);
        jPanel.add(toDate);
        jPanel.add(b1);
        jPanel.add(b2);
        add(BorderLayout.NORTH, jPanel);

        jTable = new JTable();
        jTable.setFillsViewportHeight(true);
        jTable.getSelectionModel().addListSelectionListener(new RowSelectListener());

        JScrollPane scroll = new JScrollPane(jTable);
        add(BorderLayout.CENTER, scroll);
        timeLineGraph = new BaseTimeLineGraph(null,null,null);
        add(BorderLayout.SOUTH,timeLineGraph);
    }

    ClickListener listener = new ClickListener();

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(Labels.SHIFT)) {
                ShiftDialog dialog = new ShiftDialog(parent, staffController.getSelectedItem());
                dialog.setVisible(true);

                ShiftRecord shiftRecord = dialog.getShiftRecord();
                if(shiftRecord==null)return;

                staffController.refreshStaff(staffController.getSelectedItem());
                refreshList();
            } else if (button.getText().equals(Labels.SEARCH)) {
                timeLineGraph.setFromDate(Utilities.parseDateTimeStr(fromDate.getText()));
                timeLineGraph.setToDate(Utilities.parseDateTimeStr(toDate.getText()));
                refreshList();
            }
        }
    }

}
