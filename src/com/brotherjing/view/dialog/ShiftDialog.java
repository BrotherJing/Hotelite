package com.brotherjing.view.dialog;

import com.brotherjing.controller.ShiftRecordController;
import com.brotherjing.view.Labels;
import jp.co.worksap.intern.entities.staff.ShiftRecord;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class ShiftDialog extends JDialog {

    StaffDTO staff;
    ShiftRecord shiftRecord;

    JTextField start = new JTextField(10);
    JTextField end = new JTextField(10);
    JTextField name,gender, position, staffState;
    JButton startwork = new JButton(Labels.START_WORK);
    JButton offwork = new JButton(Labels.OFF_WORK);

    public ShiftDialog(JFrame parent,StaffDTO staff){
        super(parent, "Shift", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.staff  =staff;

        setModal(true);
        setLayout(new FlowLayout());
        add(new JLabel("start date&time(yyyy/MM/dd HH:mm)"));
        add(start);
        add(new JLabel("end date&time(yyyy/MM/dd HH:mm)"));
        add(end);
        JPanel customerInfo = new JPanel(new GridLayout(4,2));
        customerInfo.add(new JLabel("Name"));
        customerInfo.add(name = new JTextField(10));
        customerInfo.add(new JLabel("Gender"));
        customerInfo.add(gender = new JTextField(10));
        customerInfo.add(new JLabel("Position"));
        customerInfo.add(position = new JTextField(10));
        customerInfo.add(new JLabel("Staff State"));
        customerInfo.add(staffState = new JTextField(10));
        add(customerInfo);

        initData();

        initListener();

        add(startwork);
        add(offwork);

        setSize(300,500);
    }

    private boolean isEmpty(){
        return (start.getText().isEmpty()||end.getText().isEmpty()||name.getText().isEmpty());
    }

    private void initData(){
        Calendar calendar = Calendar.getInstance();
        start.setText(Utilities.formatDateTime(calendar.getTime()));
        end.setText(Utilities.formatDateTime(calendar.getTime()));

        name.setText(staff.getName());
        gender.setText(staff.getGender());
        position.setText(staff.getPosition());
        staffState.setText(staff.getStaffState());
    }

    private void initListener(){

        //startwork the room without reservation
        startwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isEmpty())return;
                shiftRecord = new ShiftRecord(staff.getStaffId(),Utilities.parseDateTimeStr(start.getText()),Utilities.parseDateTimeStr(end.getText()));
                ShiftRecordController.saveRecord(shiftRecord);
                dispose();
            }
        });

        offwork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public ShiftRecord getShiftRecord() {
        return shiftRecord;
    }
}
