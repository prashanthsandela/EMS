package com.ems.testData.mongo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author siddarth
 *
 */

public class PopulateTestData {

	final static Logger logger = LogManager.getLogger(PopulateTestData.class);	
	private final String urlName = "http://api.randomuser.me/?results=1000";
	
	
	/**
	 * 
	 * Fetches data from random user generator
	 * 
	 * @return List of user info objects
	 * @throws Exception
	 */
	public List<Users> getMockData() throws Exception{
		
		logger.info("Inside getMockData()...");
		
		StringBuilder jsonArray = new StringBuilder();
		String output = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Results data = null;
		try
		{
			URL url = new URL(urlName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if(conn.getResponseCode()!=200){
				throw new RuntimeException("Failed : Http Error Code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new 
					InputStreamReader(conn.getInputStream()));
			
			while((output=br.readLine())!=null){
				jsonArray.append(output.trim());
			}
			
			data = mapper.readValue(jsonArray.toString(), Results.class);
			logger.info("Finished fetching mock data from random user api");
			
		}catch(Exception e){
			logger.debug("Exception caught in generating mock data : + "+e);
		}			
		
		return data.getResults();
	}
		
}
