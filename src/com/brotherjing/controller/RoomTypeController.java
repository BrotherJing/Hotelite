package com.brotherjing.controller;

import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomDTOReader;
import jp.co.worksap.intern.entities.room.RoomTypeDTO;
import jp.co.worksap.intern.entities.room.RoomTypeDTOReader;
import jp.co.worksap.intern.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class RoomTypeController {

    public static void saveToDatabase()throws IOException {
        try {

            Session session = HibernateUtil.getSession();
            Transaction tx = session.beginTransaction();

            List<RoomTypeDTO> csvList = new RoomTypeDTOReader().getValues();

            for(RoomTypeDTO dto:csvList){
                session.save(dto);
            }

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
