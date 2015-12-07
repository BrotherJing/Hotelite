package com.brotherjing.controller;

import jp.co.worksap.intern.entities.customer.CustomerDTO;
import jp.co.worksap.intern.entities.customer.CustomerDTOReader;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomDTOReader;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class CustomerController {

    List<CustomerDTO> customerList = null;

    public CustomerController() throws IOException {
        //this.roomList = roomList;
        loadFromDatabase();
    }

    public static void saveToDatabase()throws IOException{
        try {

            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<CustomerDTO> csvList = new CustomerDTOReader().getValues();

            for(CustomerDTO dto:csvList){
                session.save(dto);
            }

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private void loadFromDatabase(){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s", CustomerDTO.class.getName());
        Query query = session.createQuery(hql);
        customerList = query.list();
        session.close();
    }

    public static CustomerDTO find(String name){
        Session session = HibernateUtil.getSession();
        String hql = String.format("FROM %s C WHERE C.name = :mname", CustomerDTO.class.getName());
        Query query = session.createQuery(hql);
        query.setParameter("mname",name);
        List<CustomerDTO> result = query.list();
        session.close();
        if(result.size()==0)return null;
        return result.get(0);
    }

    public static void addCustomer(CustomerDTO customer){
        try {
            Session session = HibernateUtil.getFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(customer);
            t.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
