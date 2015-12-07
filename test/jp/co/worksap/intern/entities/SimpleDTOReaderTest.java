package jp.co.worksap.intern.entities;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.customer.CustomerDTO;
import jp.co.worksap.intern.entities.customer.CustomerDTOReader;
import jp.co.worksap.intern.entities.hotel.HotelMstDTO;
import jp.co.worksap.intern.entities.hotel.HotelMstDTOReader;
import jp.co.worksap.intern.entities.region.RegionMstDTO;
import jp.co.worksap.intern.entities.region.RegionMstDTOReader;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomDTOReader;
import jp.co.worksap.intern.entities.room.RoomTypeDTO;
import jp.co.worksap.intern.entities.room.RoomTypeDTOReader;
import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffDTOReader;

import jp.co.worksap.intern.util.Preference;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class SimpleDTOReaderTest {

	@Test
	public void readCustomerTable() throws IOException {
		CustomerDTOReader reader = new CustomerDTOReader();
		List<CustomerDTO> values = reader.getValues();
		assertNotNull(values);
		for (CustomerDTO value : values) {
			assertNotNull(value);
			assertNotNull(value.getAddress());
			assertNotNull(value.getBirthday());
			assertNotNull(value.getEmail());
			assertNotNull(value.getGender());
			assertNotNull(value.getName());
			assertNotNull(value.getNationality());
			assertNotNull(value.getPassportNo());
			assertNotNull(value.getTel());
		}
	}
	
	@Test
	public void readRoomTable() throws IOException{
		RoomDTOReader reader = new RoomDTOReader();
		List<RoomDTO> values = reader.getValues();
		try {  

			
            org.hibernate.SessionFactory sf = new Configuration().configure()  
                    .buildSessionFactory();  
            Session session = sf.openSession();  
            Transaction tx = session.beginTransaction();  

			for(RoomDTO dto:values){
				assertNotNull(dto);
	            session.save(dto);
			}
			
            tx.commit();  
            session.close();  
  
        } catch (HibernateException e) {  
            e.printStackTrace();  
        }
	}
	
	@Test
	public void readRoomTypeTable() throws IOException{
		RoomTypeDTOReader reader = new RoomTypeDTOReader();
		List<RoomTypeDTO> values = reader.getValues();
		try {  

			
            org.hibernate.SessionFactory sf = new Configuration().configure()  
                    .buildSessionFactory();  
            Session session = sf.openSession();  
            Transaction tx = session.beginTransaction();  

			for(RoomTypeDTO dto:values){
				assertNotNull(dto);
	            session.save(dto);
			}
			
            tx.commit();  
            session.close();  
  
        } catch (HibernateException e) {  
            e.printStackTrace();  
        }
	}

	@Test
	public void readEmployeeTable() throws IOException {
		StaffDTOReader reader = new StaffDTOReader(Constants.DEFAULT_CSV_FOLDER
				+ "STAFF_MST.csv");
		List<StaffDTO> values = reader.getValues();
		assertNotNull(values);
		for (StaffDTO value : values) {
			assertNotNull(value);
			assertNotNull(value.getStaffId());
			assertNotNull(value.getGender());
			assertNotNull(value.getName());
			assertNotNull(value.getPosition());
			assertNotNull(value.getRank());
			assertNotNull(value.getHotelId());
		}
	}

	@Test
	public void readHotelMstTable() throws IOException {
		HotelMstDTOReader reader = new HotelMstDTOReader();
		List<HotelMstDTO> values = reader.getValues();
		assertNotNull(values);
		for (HotelMstDTO value : values) {
			assertNotNull(value);
			assertNotNull(value.getAddress());
			assertNotNull(value.getHotelId());
			assertNotNull(value.getHotelName());
			assertNotNull(value.getTel());
		}
	}

	@Test
	public void readRegionTable() throws IOException {
		RegionMstDTOReader reader = new RegionMstDTOReader();
		List<RegionMstDTO> values = reader.getValues();
		assertNotNull(values);
		for (RegionMstDTO value : values) {
			assertNotNull(value);
			assertNotNull(value.getManagerId());
			assertNotNull(value.getName());
			assertNotNull(value.getRegionId());
		}
	}

    @Test
    public void readPreferenceTable() throws IOException {
        Preference reader = new Preference();
        List<PreferenceItem> values = reader.getValues();
        assertNotNull(values);
        for (PreferenceItem value : values) {
            assertNotNull(value);
        }
    }
}
