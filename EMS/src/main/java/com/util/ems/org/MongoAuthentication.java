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

import org.apache.log4j.LogManager;
import org.apache.log4j.*;

import com.ems.testData.mongo.PopulateTestData;

public class MongoAuthentication {
	
	final static Logger logger = LogManager.getLogger(PopulateTestData.class);	
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
