package jp.co.worksap.intern.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory factory = null;

	public static SessionFactory getFactory(){
		if(factory==null){
	        try{  
	            Configuration conf = new Configuration();  
	            conf.configure();  
	            ServiceRegistry sr = new ServiceRegistryBuilder()  
	                                    .applySettings(conf.getProperties())  
	                                    .buildServiceRegistry();  
	              
	            factory = conf.buildSessionFactory(sr);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }
		}
		return factory;
	}

    public static Session getSession() {
    	if(factory==null)getFactory();
        return factory.openSession();
    }
}
