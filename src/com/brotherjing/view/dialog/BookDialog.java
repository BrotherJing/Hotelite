package com.brotherjing.view.dialog;

import com.brotherjing.controller.CustomerController;
import com.brotherjing.controller.RoomRecordController;
import com.brotherjing.view.Labels;
import jp.co.worksap.intern.entities.customer.CustomerDTO;
import jp.co.worksap.intern.entities.room.Constants;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomRecord;
import jp.co.worksap.intern.util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Brotherjing on 2015-12-05.
 */
public class BookDialog extends JDialog{

    RoomDTO room;
    CustomerDTO customer;
    RoomRecord bookRecord;
    RoomRecord record;

    boolean isOldCustomer = false;

    JTextField start = new JTextField(10);
    JTextField end = new JTextField(10);
    JTextField name,gender,passportNo,tel;
    JButton arrange = new JButton(Labels.ARRANGE);
    JButton reserve = new JButton(Labels.RESERVE);
    JButton find = new JButton("FIND");

    public BookDialog(JFrame parent,RoomDTO room){
        super(parent, "Booking", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.room = room;

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
        customerInfo.add(new JLabel("passprotNo"));
        customerInfo.add(passportNo = new JTextField(10));
        customerInfo.add(new JLabel("staffState"));
        customerInfo.add(tel = new JTextField(10));
        add(customerInfo);

        initData();

        initListener();

        add(find);
        add(arrange);
        add(reserve);

        setSize(300,500);
    }

    private boolean isEmpty(){
        return (start.getText().isEmpty()||end.getText().isEmpty()||name.getText().isEmpty());
    }

    private void initData(){
        Calendar calendar = Calendar.getInstance();
        start.setText(Utilities.formatDateTime(calendar.getTime()));
        end.setText(Utilities.formatDateTime(calendar.getTime()));
    }

    private void initListener(){

        //startwork the room without reservation
        arrange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isEmpty())return;
                find();
                if(!isOldCustomer){
                    CustomerController.addCustomer(customer=new CustomerDTO(name.getText(),gender.getText(),null,null,passportNo.getText(),null,null,tel.getText()));
                    customer = CustomerController.find(name.getText());
                }
                if (bookRecord != null) {//this room is already reserved
                    bookRecord.setRecordType(Constants.ARRANGE);
                    RoomRecordController.updateRecordType(bookRecord);
                }else{
                    if(!checkAvailable())return;
                    record = new RoomRecord(Constants.ARRANGE,room.getRoomId(),customer.getCustomerId(),null, Utilities.parseDateTimeStr(start.getText()), Utilities.parseDateTimeStr(end.getText()));
                    RoomRecordController.saveRecord(record);
                }
                dispose();
            }
        });

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });

        reserve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isEmpty()||!checkAvailable())return;
                find();
                if(!isOldCustomer){
                    CustomerController.addCustomer(customer=new CustomerDTO(name.getText(),gender.getText(),null,null,passportNo.getText(),null,null,tel.getText()));
                    customer = CustomerController.find(name.getText());
                }
                record = new RoomRecord(Constants.BOOK,room.getRoomId(),customer.getCustomerId(),null, Utilities.parseDateTimeStr(start.getText()), Utilities.parseDateTimeStr(end.getText()));
                RoomRecordController.saveRecord(record);
                dispose();
            }
        });
    }

    private boolean checkAvailable(){

        Date s = Utilities.parseDateTimeStr(start.getText());
        Date e = Utilities.parseDateTimeStr(end.getText());

        return RoomRecordController.checkAvailable(room,s,e);

    }

    /**
     * find if the customer is old customer, and if the room is already reserved by him.
     */
    private void find(){
        if(name.getText().isEmpty())return;
        customer = CustomerController.find(name.getText());//old customer?
        if(customer!=null){
            gender.setText(customer.getGender());
            passportNo.setText(customer.getPassportNo());
            tel.setText(customer.getTel());
            isOldCustomer = true;
            System.out.println("old customer!!");
            bookRecord = RoomRecordController.findBookRecord(customer,room);
            if(bookRecord!=null){
                System.out.println("book record!");
                start.setText(Utilities.formatDateTime(bookRecord.getRecordStartTime()));
                end.setText(Utilities.formatDateTime(bookRecord.getRecordEndTime()));
            }
        }
    }

    public RoomRecord getRoomRecord(){
        //booked room
        if(bookRecord!=null)return bookRecord;

        //new room
        return record;
    }



}
