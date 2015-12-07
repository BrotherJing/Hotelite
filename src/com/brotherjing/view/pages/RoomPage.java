package com.brotherjing.view.pages;

import com.brotherjing.controller.CustomerController;
import com.brotherjing.controller.RoomController;
import com.brotherjing.controller.RoomRecordController;
import com.brotherjing.global.CustomerRequestQueue;
import com.brotherjing.view.dialog.BookDialog;
import com.brotherjing.view.Labels;
import com.brotherjing.view.timeline.RoomTableModel;
import com.brotherjing.view.timeline.TimeLineCellRenderer;
import com.brotherjing.view.timeline.TimeLineGraph;
import jp.co.worksap.intern.entities.customer.CustomerRequest;
import jp.co.worksap.intern.entities.room.Constants;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomRecord;
import jp.co.worksap.intern.entities.room.RoomTimeLine;
import jp.co.worksap.intern.util.Utilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class RoomPage extends JPanel {

    private static final Log logger = LogFactory.getLog(RoomPage.class);

    private JFrame parent;

    private RoomController roomController;
    private CustomerController customerController;

    private TimeLineGraph timeLineGraph;
    private JList jList;
    private JTable jTable;
    private JButton b1 = new JButton(Labels.SEARCH), b2 = new JButton(Labels.RESERVE),checkout = new JButton(Labels.CHECK_OUT);
    private JTextField fromDate = new JTextField(20), toDate = new JTextField(20);

    private String[] headers = {"ROOM NUMBER", "STATE", "TIME LINE"};

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(Labels.SEARCH)) {
                if (fromDate.getText().isEmpty() || toDate.getText().isEmpty()) return;
                timeLineGraph.setFromDate(Utilities.parseDateTimeStr(fromDate.getText()));
                timeLineGraph.setToDate(Utilities.parseDateTimeStr(toDate.getText()));
                roomController.range(Utilities.parseDateTimeStr(fromDate.getText()), Utilities.parseDateTimeStr(toDate.getText()));
                refreshList();
            } else if (button.getText().equals(Labels.RESERVE)) {
                BookDialog dialog = new BookDialog(parent, roomController.getSelectedItem());
                //System.out.println(roomController.getSelectedItem().toString());
                dialog.setVisible(true);
                RoomRecord record = dialog.getRoomRecord();

                if (record == null) {
                    System.out.println("booking is cancelled");
                    return;
                }

                roomController.refreshRoom(roomController.getSelectedItem());
                refreshList();
            }else if(button.getText().equals(Labels.CHECK_OUT)){
                RoomDTO room = roomController.getSelectedItem();
                room.setState(Constants.STATE_IDLE);
                RoomRecordController.deleteArrangeRecordByRoom(room);
                refreshList();
                int res = JOptionPane.showConfirmDialog(parent,"Clean the room now?","Clean",JOptionPane.YES_NO_OPTION);
                if(res==0){
                    CustomerRequestQueue.enqueue(new CustomerRequest(new Date(),"clean room "+room.getRoomNumber(),room.getCustomerId()));
                }
            }
        }
    }

    private ClickListener listener = new ClickListener();

    private class RowSelectListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() || jTable.getSelectionModel().getLeadSelectionIndex() < 0) {
                return;
            }
            roomController.setSelected(jTable.getSelectionModel().getLeadSelectionIndex());
            timeLineGraph.setTimeLine(roomController.getSelectedItem().getTimeLine());

        }
    }

    public RoomPage(JFrame parent) {

        this.parent = parent;

        initView();

        initData();

        fillData();
    }

    private void fillData() {
        if (roomController.getRoomList() == null) return;
        jList = new JList();

        jTable = new JTable();
        jTable.setFillsViewportHeight(true);
        jTable.getSelectionModel().addListSelectionListener(new RowSelectListener());

        refreshList();

        //JScrollPane scroll = new JScrollPane(jList);
        JScrollPane scroll = new JScrollPane(jTable);
        //jList.addMouseListener(mouseListener);
        add(BorderLayout.CENTER, scroll);
    }

    private void refreshList() {
        roomController.range(Utilities.parseDateTimeStr(fromDate.getText()), Utilities.parseDateTimeStr(toDate.getText()));
        DefaultTableModel model = new RoomTableModel(null, headers);
        for (RoomDTO room : roomController.getRoomList()) {
            roomController.refreshRoom(room);
            model.addRow(new Object[]{room.getRoomNumber(), room.getState(), room.getTimeLine()});
        }
        jTable.setModel(model);
        jTable.setDefaultRenderer(RoomTimeLine.class, new TimeLineCellRenderer());
        initColumnSize(jTable);
        jTable.repaint();
    }

    private void initColumnSize(JTable table){
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);
    }

    private void initData() {
        try {
            roomController = new RoomController();
            customerController = new CustomerController();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        roomController.range(from, calendar.getTime());

    }

    private void initView() {
        b1.addActionListener(listener);
        b2.addActionListener(listener);
        checkout.addActionListener(listener);
        setLayout(new BorderLayout());
        //add(txt);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(fromDate);
        jPanel.add(toDate);
        jPanel.add(b1);
        jPanel.add(b2);
        jPanel.add(checkout);
        add(BorderLayout.NORTH, jPanel);

        timeLineGraph = new TimeLineGraph(null,null, null);
        add(BorderLayout.SOUTH, timeLineGraph);
    }

}
