package com.ems.testData.mongo;

/**
 * @author siddarth
 */

import org.junit.Test;

/**
 * Calls pushDataToDB method
 */
public class PushDataToDBTest {

	@Test
	public void testPopulateUserTable() {
		PushDataToDB testObj = new PushDataToDB();
		testObj.populateUserTable();
	}

}
