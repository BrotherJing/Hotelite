package com.brotherjing.controller;

import jp.co.worksap.intern.entities.customer.CustomerDTO;
import jp.co.worksap.intern.entities.room.Constants;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomRecord;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-05.
 */
public class RoomRecordController {

    public static void saveRecord(RoomRecord record) {
        try {
            Session session = HibernateUtil.getFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(record);
            t.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static RoomRecord findBookRecord(CustomerDTO customer,RoomDTO room){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s R WHERE R.customerId = :mCustomerId AND R.roomId = :mRoomId AND R.recordType = :mType", RoomRecord.class.getName());
        Query query = session.createQuery(hql);
        query.setParameter("mCustomerId",customer.getCustomerId());
        query.setParameter("mRoomId",room.getRoomId());
        query.setParameter("mType",Constants.BOOK);
        List<RoomRecord> result = query.list();
        session.close();
        if(result.size()==0)return null;
        return result.get(0);
    }

    public static void updateRecordType(RoomRecord record){
        try {
            Session session = HibernateUtil.getFactory().openSession();
            Transaction t = session.beginTransaction();
            String hql = String.format("UPDATE %s R SET R.recordType = :newType WHERE R.recordId = :myRecordId", RoomRecord.class.getName());
            Query query = session.createQuery(hql);
            query.setParameter("newType",record.getRecordType());
            query.setParameter("myRecordId",record.getRecordId());
            int res = query.executeUpdate();
            t.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArrangeRecordByRoom(RoomDTO room){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        String hql = String.format("FROM %s R WHERE R.roomId=:myRoomId AND R.recordType = :mState", RoomRecord.class.getName());
        Query query = session.createQuery(hql);
        query.setParameter("mState", Constants.ARRANGE);
        query.setParameter("myRoomId", room.getRoomId());
        List<RoomRecord> result = query.list();
        System.out.println(result.size()+"aas;lefijafije");
        if(result.size()!=0)session.delete(result.get(0));
        transaction.commit();
        session.close();
    }

    public static boolean checkAvailable(RoomDTO room,Date start,Date end){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s R WHERE R.roomId=:myRoomId AND R.recordStartTime <= :end AND R.recordEndTime >= :start", RoomRecord.class.getName());
        Query query = session.createQuery(hql);
        query.setTime("start",start);
        query.setTime("end",end);
        query.setParameter("myRoomId",room.getRoomId());
        List<RoomRecord> result = query.list();
        session.close();
        return result.size()==0;
    }

}
