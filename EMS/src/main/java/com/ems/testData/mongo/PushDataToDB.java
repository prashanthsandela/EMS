package com.ems.testData.mongo;

/**
 * @author siddarth
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.util.ems.org.MongoAuthentication;

public class PushDataToDB {
	
	final static Logger logger = LogManager.getLogger(PushDataToDB.class);
	private PopulateTestData testData = null;
	private MongoAuthentication mauth = null;
	private List<String> auth = null;
	private MongoClient mongo = null;
	
	
	/**
	 * Creates "TestDatabase" "userTestData" collection 
	 * Pushes 1000 docs of mockdata on each call
	 */
	public void populateUserTable(){
		
		try
		{
		
			testData = new PopulateTestData();
			mauth = new MongoAuthentication();
			auth = mauth.getMongoAuth();
			
			mongo = new MongoClient(auth.get(0).toString()
					,Integer.parseInt(auth.get(1).toString()));
			MongoDatabase testDB = mongo.getDatabase("TestDatabase");
			MongoCollection<Document> userCollection = testDB.getCollection("userTestData");
			
			List<Document> docBuilder = new ArrayList<>();
			List<Users> mockData = testData.getMockData();
			
			logger.info("Building documents array ---> Pushing to DB <userTestData> collection in database <TestDatabase>");
			
			for(Users obj1 : mockData){
				
				User obj = obj1.getUser();
				Document user = new Document();
				
				user.append("Email",obj.getEmail());
				user.append("Username",obj.getUsername());
				user.append("Password",obj.getPassword());
				user.append("Registered",obj.getRegistered());
				user.append("Phone", obj.getPhone());
				user.append("Firstname", obj.getName().getFirst());
				user.append("Lastname", obj.getName().getLast());
				user.append("Title", obj.getName().getTitle());
				user.append("gender", obj.getGender());
				
				Document location = new Document();
				
				location.append("Street", obj.getLocation().getStreet());
				location.append("City", obj.getLocation().getCity());
				location.append("State", obj.getLocation().getState());
				location.append("Zip", obj.getLocation().getZip());			
				user.append("Location", location);
				docBuilder.add(user);
	
			}
			
			logger.info("Pushing docs to db");
			userCollection.insertMany(docBuilder);
			mongo.close();
			
		}catch(MongoException e){
			logger.debug("Failed to connect to mongoDB : "+e);
		}catch(IOException e){
			logger.debug("IOException : "+e);
		}catch(Exception e){
			logger.debug("Exception : "+e);
		}
		
	}	
}
