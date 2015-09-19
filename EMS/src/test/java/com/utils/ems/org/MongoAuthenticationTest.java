package com.utils.ems.org;

import java.io.IOException;

import org.junit.Test;

import com.util.ems.org.MongoAuthentication;

public class MongoAuthenticationTest {
	
	@Test
	public void testGetMongoAuth(){
		MongoAuthentication testObject = new MongoAuthentication();
		try{
			for(String e : testObject.getMongoAuth()){
				System.out.println(e);
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
}

