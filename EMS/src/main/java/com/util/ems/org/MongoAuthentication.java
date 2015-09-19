package com.util.ems.org;

/**
 * @author siddarth
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.glassfish.hk2.utilities.reflection.Logger;

public class MongoAuthentication {
	
	final static Logger logger = Logger.getLogger();	
	private static String propName = "mongodb.properties";
	
	/**
	 * Returns MongoDB login credentials
	 * @return List<String>, Ex: ["localhost","27017"]
	 */
	public List<String> getMongoAuth() throws IOException{
		
		logger.debug("Entered getMongoAuth");
		
		List<String> auth = new ArrayList<String>();
		Properties prop = new Properties();
		InputStream inputstream = null;
		
		inputstream = getClass().getClassLoader().getResourceAsStream(propName);
		
		if(inputstream!=null){
			prop.load(inputstream);
			auth.add(prop.getProperty("ip"));
			auth.add(prop.getProperty("port"));
		}else{
			logger.debug("Property file <"+propName+"> not found!");
			throw new FileNotFoundException("Property file <"+propName+"> not found!");
		}
		return auth;
	}
}
