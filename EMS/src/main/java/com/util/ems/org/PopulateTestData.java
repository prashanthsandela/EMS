package com.util.ems.org;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.glassfish.hk2.utilities.reflection.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author siddarth
 *
 */

public class PopulateTestData {

	final static Logger logger = Logger.getLogger();	
	private final String urlName = "http://api.randomuser.me/?results=200";
	
	public List<UserPojo> getMockData() throws Exception{
		
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
				jsonArray.append(output);
			}
			
			data = mapper.readValue(jsonArray.toString(), Results.class);
			
		}catch(Exception e){
			System.out.println("Exception caught in generating mock data : + "+e);
			logger.debug("Exception caught in generating mock data : + "+e);
		}			
		
		return data.getResults();
	}
		
}
