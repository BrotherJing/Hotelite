package com.brotherjing.controller;

import jp.co.worksap.intern.entities.customer.CustomerDTO;
import jp.co.worksap.intern.entities.customer.CustomerDTOReader;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomRecord;
import jp.co.worksap.intern.entities.room.RoomTimeLine;
import jp.co.worksap.intern.entities.staff.Constants;
import jp.co.worksap.intern.entities.staff.ShiftRecord;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffDTOReader;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class StaffController {

    private List<StaffDTO> staffList;
    private int selected;

    public StaffDTO getSelectedItem(){
        if(selected<0)return null;
        return staffList.get(selected);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public StaffController(){
        loadFromDatabase();
    }

    public List<StaffDTO> getStaffList() {
        return staffList;
    }

    public void loadFromDatabase(){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s", StaffDTO.class.getName());
        Query query = session.createQuery(hql);
        staffList = query.list();
        session.close();

    }

    public void loadAvailableStaff(){

        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s S WHERE S.staffState != :state", StaffDTO.class.getName());
        Query query = session.createQuery(hql);
        query.setParameter("state",Constants.NOT_WORKING);
        staffList = query.list();
        session.close();
    }

    public static void saveToDatabase()throws IOException {
        try {

            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<StaffDTO> csvList = new StaffDTOReader().getValues();

            for(StaffDTO dto:csvList){
                session.save(dto);
            }

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void assignTask(StaffDTO staff){
        try {
            //refresh room state
            Session session = HibernateUtil.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String hql = String.format("UPDATE %s R SET R.staffState = :newState, R.taskId = :mTaskId WHERE R.staffId = :myStaffId", StaffDTO.class.getName());
            Query query = session.createQuery(hql);
            query.setParameter("newState",staff.getStaffState());
            query.setParameter("myStaffId", staff.getStaffId());
            query.setParameter("mTaskId", staff.getTaskId());
            int res = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void refreshStaff(StaffDTO staff){
        try {
            //search room record that affect the state
            Session session = HibernateUtil.getFactory().openSession();
            String hql = String.format("FROM %s R WHERE R.staffId=:myStaffId AND R.recordStartTime <= :current AND R.recordEndTime >= :current", ShiftRecord.class.getName());
            Query query = session.createQuery(hql);
            query.setParameter("myStaffId", staff.getStaffId());
            query.setTime("current", new Date());
            List<ShiftRecord> result = query.list();
            session.close();
            for(ShiftRecord r:result){
                staff.setStaffState(Constants.IDLE);
            }
            if(result.size()==0){
                staff.setStaffState(Constants.NOT_WORKING);
            }

            //refresh room state
            session = HibernateUtil.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            hql = String.format("UPDATE %s R SET R.staffState = :newState WHERE R.staffId = :myStaffId", StaffDTO.class.getName());
            query = session.createQuery(hql);
            query.setParameter("newState",staff.getStaffState());
            query.setParameter("myStaffId", staff.getStaffId());
            int res = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void range(Date from,Date to){
        Session session = HibernateUtil.getFactory().openSession();
        String hql = String.format("FROM %s R WHERE R.recordStartTime <= :toDate AND R.recordEndTime >= :fromDate ORDER BY R.staffId ASC", ShiftRecord.class.getName());
        Query query = session.createQuery(hql);
        query.setTime("toDate", to);
        query.setTime("fromDate", from);
        List<ShiftRecord> result = query.list();

        int i=0;
        for(StaffDTO staff:staffList){
            List<ShiftRecord> record = new ArrayList<ShiftRecord>();
            while(i<result.size()&&staff.getStaffId()==result.get(i).getStaffId()){
                record.add(result.get(i));
                ++i;
            }
            staff.setShiftRecordList(record);
        }
        session.close();
    }

}
