package com.brotherjing.view.pages;

import com.brotherjing.controller.StaffController;
import com.brotherjing.controller.StaffTaskController;
import com.brotherjing.global.CustomerRequestQueue;
import com.brotherjing.socket.NetworkUtil;
import com.brotherjing.socket.ReadHandlerThread;
import com.brotherjing.view.Hotel;
import com.brotherjing.view.Labels;
import jp.co.worksap.intern.entities.customer.CustomerRequest;
import jp.co.worksap.intern.entities.staff.Constants;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffTask;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class ServicePage extends JPanel {

    private JFrame parent;
    private JTable jTable;

    private JLabel ipAddr=new JLabel(),message=new JLabel();
    private JButton b1 = new JButton(Labels.ASSIGN), b2 = new JButton(Labels.FINISH);
    private JButton customer = new JButton(Labels.CUSTOMER_CALL),refresh = new JButton(Labels.REFRESH);

    private StaffController staffController;

    private String[] headers = {"STAFF NAME", "STATE", "PROGRESS"};

    private class RowSelectListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() || jTable.getSelectionModel().getLeadSelectionIndex() < 0) {
                return;
            }

            staffController.setSelected(jTable.getSelectionModel().getLeadSelectionIndex());
        }
    }

    public ServicePage(JFrame parent) {

        this.parent = parent;

        initData();

        initView();

        fillData();

    }

    private ReadHandlerThread.OnReceiveListener listener = new ReadHandlerThread.OnReceiveListener() {
        @Override
        public void onReceive(String msg) {
            message.setText(msg);
        }
    };

    private void fillData(){
        try {
            ipAddr.setText(NetworkUtil.getIp());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        Hotel.tcpServer.setListener(listener);

        showNextRequest();
    }

    private void refreshList() {
        staffController.loadAvailableStaff();
        DefaultTableModel model = new DefaultTableModel(null, headers);
        for (StaffDTO staff:staffController.getStaffList()) {
            Object[] row = new Object[3];
            row[0]=staff.getName();
            row[1]= staff.getStaffState();
            row[2]="";
            if(staff.getTaskId()!=null){
                StaffTask task = StaffTaskController.findById(staff.getTaskId());
                if(task!=null&&!task.getTaskState().equals(Constants.FINISHED)) {
                    System.out.println("my task is " + task.getDesc());
                    row[2] = task.getDesc() + ": " + ((new Date().getTime() - task.getTaskStartTime().getTime()) / 1000);
                }
            }
            model.addRow(row);
        }
        jTable.setModel(model);
        jTable.repaint();
    }

    private void initData() {
        staffController = new StaffController();
        staffController.loadAvailableStaff();
    }

    private void initView() {
        b1.addActionListener(clickListener);
        b2.addActionListener(clickListener);
        customer.addActionListener(clickListener);
        refresh.addActionListener(clickListener);
        setLayout(new BorderLayout());
        //add(txt);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(ipAddr);
        jPanel.add(message);
        jPanel.add(b1);
        jPanel.add(b2);
        jPanel.add(customer);
        jPanel.add(refresh);
        add(BorderLayout.NORTH, jPanel);

        jTable = new JTable();
        jTable.setFillsViewportHeight(true);
        jTable.getSelectionModel().addListSelectionListener(new RowSelectListener());
        refreshList();

        JScrollPane scroll = new JScrollPane(jTable);
        add(BorderLayout.CENTER, scroll);
    }


    private ClickListener clickListener = new ClickListener();

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(Labels.ASSIGN)) {
                doAssign();
            } else if (button.getText().equals(Labels.CUSTOMER_CALL)) {
                CustomerRequestQueue.enqueue(new CustomerRequest(new Date(),"Room 1001: clean my room",1));
                showNextRequest();
            }else if(button.getText().equals(Labels.REFRESH)){
                showNextRequest();
                refreshList();
            }else if(button.getText().equals(Labels.FINISH)){
                finishTask();
            }
        }
    }

    private void showNextRequest(){
        CustomerRequest request = CustomerRequestQueue.peek();
        if(request!=null){
            message.setText(request.getRequestContent());
        }else{
            message.setText("");
        }
    }

    private void finishTask(){
        StaffDTO staffDTO = staffController.getSelectedItem();
        if(!staffDTO.getStaffState().equals(Constants.BUSY))return;

        staffDTO.setStaffState(Constants.IDLE);
        staffController.refreshStaff(staffDTO);

        StaffTask task = StaffTaskController.findById(staffDTO.getTaskId());
        StaffTaskController.finishTask(task);

        refreshList();

    }

    private void doAssign(){
        StaffDTO staffDTO = staffController.getSelectedItem();
        System.out.println(staffDTO.getName());
        if(!staffDTO.getStaffState().equals(Constants.IDLE))return;
        //if(CustomerRequestQueue.isEmpty())return;

        CustomerRequest request = CustomerRequestQueue.dequeue();
        StaffTask task = new StaffTask(staffDTO.getStaffId(),new Date(),null,Constants.IN_PROGRESS,request.getRequestContent());
        Integer id = StaffTaskController.saveRecord(task);
        task.setTaskId(id);

        staffDTO.setStaffState(Constants.BUSY);
        staffDTO.setTaskId(task.getTaskId());
        staffController.assignTask(staffDTO);
        refreshList();

        showNextRequest();
    }
}
