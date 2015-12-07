package com.brotherjing.controller;

import jp.co.worksap.intern.entities.staff.ShiftRecord;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class ShiftRecordController {

    public static void saveRecord(ShiftRecord record) {
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

}
