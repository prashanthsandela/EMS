package com.ems.testData.mongo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author siddarth
 */

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PopulateTestDataTest {

	final static Logger logger = LogManager.getLogger(PopulateTestData.class);
	private final String urlName = "http://api.randomuser.me/?results=10";
	
	@Test
	public void testGetMockData() {
		
		StringBuilder jsonArray = new StringBuilder();
		String output = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Results data = null;
		List<Users> totalUsers = null;
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
			
			System.out.println(jsonArray.toString());
			data = mapper.readValue(jsonArray.toString(), Results.class);
			System.out.println(data.getResults());
			totalUsers = data.getResults();
			for(Users obj1 : totalUsers){
				User obj = obj1.getUser();
				System.out.println(obj.getEmail());
			}
			
			
		}catch(Exception e){
			System.out.println("Exception caught in generating mock data : + "+e);
			logger.debug("Exception caught in generating mock data : + "+e);
		}			

		
	}

}
