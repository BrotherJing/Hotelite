package com.brotherjing.controller;

import jp.co.worksap.intern.entities.staff.Constants;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffTask;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class StaffTaskController {

    public static Integer saveRecord(StaffTask task) {
        try {
            Session session = HibernateUtil.getFactory().openSession();
            Transaction t = session.beginTransaction();
            Integer id = (Integer)session.save(task);
            System.out.println(id+"the new id");
            t.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static StaffTask findById(Integer id){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s R WHERE R.taskId = :mId", StaffTask.class.getName());
        Query query = session.createQuery(hql);
        query.setParameter("mId",id);
        List<StaffTask> result = query.list();
        session.close();
        if(result.size()==0)return null;
        return result.get(0);
    }

    public void assignTask(StaffDTO staff){
        try {
            //refresh room state
            Session session = HibernateUtil.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String hql = String.format("UPDATE %s R SET R.staffState = :newState WHERE R.staffId = :myStaffId", StaffDTO.class.getName());
            Query query = session.createQuery(hql);
            query.setParameter("newState",staff.getStaffState());
            query.setParameter("myStaffId", staff.getStaffId());
            int res = query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static void finishTask(StaffTask task){
        try {
            //refresh room state
            Session session = HibernateUtil.getFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
