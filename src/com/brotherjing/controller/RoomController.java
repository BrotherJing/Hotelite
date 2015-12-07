package com.brotherjing.controller;

import javafx.animation.Timeline;
import jp.co.worksap.intern.entities.room.*;
import jp.co.worksap.intern.util.HibernateUtil;
import jp.co.worksap.intern.util.Utilities;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-05.
 */
public class RoomController {

    List<RoomDTO> roomList = null;
    int selected;

    public RoomController() throws IOException{
        //this.roomList = roomList;
        loadFromDatabase();
    }

    public List<RoomDTO> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomDTO> roomList) {
        this.roomList = roomList;
    }

    public RoomDTO getSelectedItem(){
        return roomList.get(selected);
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void loadFromDatabase(){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s", RoomDTO.class.getName());
        Query query = session.createQuery(hql);
        roomList = query.list();
        session.close();

    }

    public static void saveToDatabase()throws IOException{
        try {

            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<RoomDTO> csvList = new RoomDTOReader().getValues();

            for(RoomDTO dto:csvList){
                session.save(dto);
            }

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    //refresh current room state
    public void refreshRoom(RoomDTO room){
        try {
            //search room record that affect the state
            Session session = HibernateUtil.getFactory().openSession();
            String hql = String.format("FROM %s R WHERE R.roomId=:myRoomId AND R.recordStartTime <= :current AND R.recordEndTime >= :current", RoomRecord.class.getName());
            Query query = session.createQuery(hql);
            query.setParameter("myRoomId", room.getRoomId());
            query.setTime("current", new Date());
            List<RoomRecord> result = query.list();
            session.close();
            for(RoomRecord r:result){
                //System.out.println(Utilities.formatDateTime(r.getRecordStartTime()));
                //System.out.println(Utilities.formatDateTime(r.getRecordEndTime()));
                String recordType = r.getRecordType();
                if(recordType.equals(Constants.BOOK)){
                    room.setState(Constants.STATE_RESERVED);
                }else if(recordType.equals(Constants.ARRANGE)){
                    room.setState(Constants.STATE_OCCUPIED);
                }
                room.setCustomerId(r.getCustomerId());
            }
            if(result.size()==0){
                room.setState(Constants.STATE_IDLE);
            }

            //refresh room state
            session = HibernateUtil.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            hql = String.format("UPDATE %s R SET R.state = :newState, R.customerId = :newCustomerId WHERE R.roomId = :myRoomId", RoomDTO.class.getName());
            query = session.createQuery(hql);
            query.setParameter("newState",room.getState());
            query.setParameter("newCustomerId",room.getCustomerId());
            query.setParameter("myRoomId",room.getRoomId());
            int res = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void range(Date from,Date to){
        Session session = HibernateUtil.getFactory().openSession();
        String hql = String.format("FROM %s R WHERE R.recordStartTime <= :toDate AND R.recordEndTime >= :fromDate ORDER BY R.roomId ASC", RoomRecord.class.getName());
        Query query = session.createQuery(hql);
        query.setTime("toDate", to);
        query.setTime("fromDate", from);
        List<RoomRecord> result = query.list();

        int i=0;
        for(RoomDTO room:roomList){
            List<RoomRecord> record = new ArrayList<RoomRecord>();
            while(i<result.size()&&room.getRoomId()==result.get(i).getRoomId()){
                record.add(result.get(i));
                ++i;
            }
            room.setTimeLine(new RoomTimeLine(record,from,to));
        }
        session.close();
    }
}
