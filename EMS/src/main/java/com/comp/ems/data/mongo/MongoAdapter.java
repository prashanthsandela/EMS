/**
 * This class creates connection drives to the project. 
 * Database being used is MONGODB
 * @author Prashanth Sandela
 */
package com.comp.ems.data.mongo;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoAdapter {

	// ------- CONSTANTS -------------------------------------------------//

	private final String hostname = "localhost";
	private final int port = 27017;
	private final static String database = "local";
	private final String username = "root";
	private final String password = "donno";

	// ------- STATIC FIELDS ---------------------------------------------//

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoAdapter.class);

	// ------- INSTANCE FIELDS -------------------------------------------//

	private static MongoAdapter mongoAdapter = new MongoAdapter();

	// ------- DYNAMIC FIELDS --------------------------------------------//

	private static MongoDatabase db;
	private MongoCollection<Document> collection;
	private Map<String, String> reservedKeys = new HashMap<>();

	// ------- CONSTRUCTORS ----------------------------------------------//

	private MongoAdapter() {
		establishConnection();
		setReservedKeys();
	}

	// ------- MEMBER METHODS --------------------------------------------//

	public void setCollection(String collectionName) {
		collection = db.getCollection(collectionName);
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	private void setReservedKeys() {
		reservedKeys.put("=", "$eq");
		reservedKeys.put(">", "$gt");
		reservedKeys.put("<", "$lt");
		reservedKeys.put(">=", "$gte");
		reservedKeys.put("<=", "$lte");
		reservedKeys.put("!=", "$ne");
		reservedKeys.put("in", "$in");
		reservedKeys.put("nin", "$nin");
		reservedKeys.put("and", "$and");
		reservedKeys.put("or", "$or");
	}

	@SuppressWarnings("resource")
	private static void establishConnection() {
		LOGGER.debug("IN METHOD establishConnection");

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDatabase(database);
	}

	public static MongoAdapter getMongoAdapter() {
		return mongoAdapter;
	}

	/**
	 * Method used to insert only one document
	 * 
	 * @param collectionName
	 * @param document
	 */
	public void insertOne(String collectionName, Document document) {
		setCollection(collectionName);
		collection.insertOne(document);
	}

	public void insertOne(String collectionName, List<String> fields) {
		setCollection(collectionName);
		Document fieldsDoc = parseListFieldsToDocument(fields);
		collection.insertOne(fieldsDoc);
	}

	/**
	 * Method used to insert only many documents
	 * 
	 * @param collectionName
	 * @param document
	 */
	public void insertMany(String collectionName, List<Document> documents) {
		setCollection(collectionName);
		collection.insertMany(documents);
	}

	/**
	 * Returns complete set of document from a collection
	 * 
	 * @param collectionName
	 * @return FindIterable<Document>
	 */
	public FindIterable<Document> find(String collectionName) {
		setCollection(collectionName);
		return collection.find();
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. Example of
	 * conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")
	 * 
	 * @param collectionName
	 * @param conditions
	 * @return
	 */
	public FindIterable<Document> find(String collectionName, List<String> conditions) {
		setCollection(collectionName);
		Document document = parseListConditionsToDocument(conditions);
		return collection.find(document);
	}

	public FindIterable<Document> find(String collectionName, Document conditions) {
		setCollection(collectionName);
		return collection.find(conditions);
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * For sort: 1 => ascending and -1 => descending <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * Examples of Sort : <br />
	 * 1. List => ("firstname", "1", "lastname", "-1"); <br />
	 * Above example sorts by ascending order of firstname and descending order
	 * of lastname <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @param sort
	 * @return
	 */
	public FindIterable<Document> find(String collectionName, List<String> conditions, List<String> sort) {
		setCollection(collectionName);
		Document conditionsDoc = parseListConditionsToDocument(conditions);
		Document sortDoc = parseListSortToDocument(sort);

		return collection.find(conditionsDoc).sort(sortDoc);
	}

	public FindIterable<Document> find(String collectionName, Document conditions, Document sort) {
		setCollection(collectionName);

		return collection.find(conditions).sort(sort);
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * For sort: 1 => ascending and -1 => descending <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * Examples of Sort : <br />
	 * 1. List => ("firstname", "1", "lastname", "-1"); <br />
	 * Above example sorts by ascending order of firstname and descending order
	 * of lastname <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @param sort
	 * @param limit
	 * @return
	 */
	public FindIterable<Document> find(String collectionName, List<String> conditions, List<String> sort, int limit) {
		setCollection(collectionName);
		Document conditionsDoc = parseListConditionsToDocument(conditions);
		Document sortDoc = parseListSortToDocument(sort);

		return collection.find(conditionsDoc).sort(sortDoc).limit(limit);
	}

	public FindIterable<Document> find(String collectionName, Document conditions, Document sort, int limit) {
		setCollection(collectionName);
		return collection.find(conditions).sort(sort).limit(limit);
	}

	/**
	 * This Method parses the list of conditions to documents
	 * 
	 * @param conditions
	 * @return
	 */
	private Document parseListConditionsToDocument(List<String> conditions) {

		if (conditions == null || conditions.isEmpty()) {
			return new Document();
		}

		int size = conditions.size();
		if ((size - 1) % 3 != 0) {
			LOGGER.debug("INVALID CONDITION: {}", conditions.toString());
			return new Document();
		}

		String conditionType = reservedKeys.get(conditions.get(0));
		List<Document> conditionsList = new ArrayList<>();
		for (int index = 1; index < size; index += 3) {
			String lhs = conditions.get(index);
			String condition = reservedKeys.get(conditions.get(index + 1));
			String rhs = conditions.get(index + 2);

			conditionsList.add(new Document(lhs, new Document(condition, rhs)));
		}

		return new Document(conditionType, conditionsList);
	}

	/**
	 * Parse list of sorted request to document
	 * 
	 * @param sorts
	 * @return
	 */
	private Document parseListSortToDocument(List<String> sorts) {
		Document document = new Document();
		if (sorts == null || sorts.isEmpty()) {
			return document;
		}

		int size = sorts.size();
		if ((size) % 2 != 0) {
			LOGGER.debug("INVALID CONDITION: {}", sorts.toString());
			return document;
		}

		for (int index = 0; index < size; index += 2) {
			document.append(sorts.get(index), sorts.get(index + 1));
		}

		return document;
	}

	private Document parseListFieldsToDocument(List<String> fields) {
		Document document = new Document("$set", new Document());
		if (fields == null || fields.isEmpty()) {
			return document;
		}

		int size = fields.size();
		if ((size) % 2 != 0) {
			LOGGER.debug("INVALID CONDITION: {}", fields.toString());
			return document;
		}

		for (int index = 0; index < size; index += 2) {
			document.append(fields.get(index), fields.get(index + 1));
		}

		return document;
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @param fields
	 * @return
	 */
	public void updateOne(String collectionName, List<String> conditions, List<String> fields) {
		setCollection(collectionName);
		Document conditionDoc = parseListConditionsToDocument(conditions);
		Document FieldDoc = parseListFieldsToDocument(fields);

		collection.updateOne(conditionDoc, FieldDoc);
	}

	public void updateOne(String collectionName, Document conditions, Document fields) {
		setCollection(collectionName);
		collection.updateOne(conditions, fields);
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @param fields
	 * @return
	 */
	public void updateMany(String collectionName, List<String> conditions, List<String> fields) {
		setCollection(collectionName);
		Document conditionDoc = parseListConditionsToDocument(conditions);
		Document FieldDoc = parseListFieldsToDocument(fields);

		collection.updateMany(conditionDoc, FieldDoc);
	}

	public void updateOMany(String collectionName, Document conditions, Document fields) {
		setCollection(collectionName);
		collection.updateMany(conditions, fields);
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @return
	 */
	public void deleteMany(String collectionName, List<String> conditions) {
		setCollection(collectionName);
		Document conditionDoc = parseListConditionsToDocument(conditions);
		collection.deleteMany(conditionDoc);
	}

	public void deleteMany(String collectionName, Document conditions) {
		setCollection(collectionName);
		collection.deleteMany(conditions);
	}

	/**
	 * Return documents which match the filtered conditions <br />
	 * Current version of filter supports either complete AND or OR. <br />
	 * 
	 * Example of conditions : <br />
	 * 1. List => ("and", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24")<br />
	 * 2. List => ("or", "firstname", "=", "prashanth", "lastname", "=" ,
	 * "Sandela", "age", ">=","24") <br />
	 * 
	 * @param collectionName
	 * @param conditions
	 * @return
	 */
	public void deleteOne(String collectionName, List<String> conditions) {
		setCollection(collectionName);
		Document conditionDoc = parseListConditionsToDocument(conditions);
		collection.deleteOne(conditionDoc);
	}

	public void deleteOne(String collectionName, Document conditions) {
		setCollection(collectionName);
		collection.deleteOne(conditions);
	}

}
