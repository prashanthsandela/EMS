package com.comp.ems.data.mongo;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoAdapter {
	//------- CONSTANTS -------------------------------------------------//
	private final String hostname = "localhost";
	private final int port = 27017;
	private final static String database = "local";
	private final String username = "root";
	private final String password = "donno";

	//------- STATIC FIELDS ---------------------------------------------//

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoAdapter.class);

	//------- INSTANCE FIELDS -------------------------------------------//
	private static MongoAdapter mongoAdapter = new MongoAdapter();

	//------- DYNAMIC FIELDS --------------------------------------------//
	private static DB db;

	//------- CONSTRUCTORS ----------------------------------------------//
	private MongoAdapter() {}

	static {
		establishConnection();
	}

	private static void establishConnection() {
		LOGGER.debug("IN METHOD establishConnection");
		
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
			db = mongoClient.getDB( database );
		} catch (UnknownHostException e) {
			LOGGER.debug(e.toString());
			e.printStackTrace();
		}
	}
	
	//------- MEMBER METHODS --------------------------------------------//
	
	public static MongoAdapter getMongoAdapter() {
		return mongoAdapter;
	}
	
	public void insert(String collectionName) {}
}
