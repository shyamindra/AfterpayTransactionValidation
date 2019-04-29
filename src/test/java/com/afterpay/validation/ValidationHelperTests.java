package com.afterpay.validation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.afterpay.data.Transaction;

public class ValidationHelperTests {

	@Test
	public void testIsFraudalentTransaction() throws ParseException {
		
		Transaction testTransaction = new Transaction("10d7ce2f43e35fa57d1bbf8b1a3", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-30T13:15:54"), 10.00);
		
		assertEquals(false, new ValidationHelper().isFraudalentTransaction(
				Arrays.asList(testTransaction), 20));
		assertEquals(false, new ValidationHelper().isFraudalentTransaction(
				Arrays.asList(testTransaction), 10));
		assertEquals(true, new ValidationHelper().isFraudalentTransaction(
				Arrays.asList(testTransaction), 5));
		assertEquals(true, new ValidationHelper().isFraudalentTransaction(
				Arrays.asList(testTransaction, testTransaction, testTransaction), 20));
		assertEquals(false, new ValidationHelper().isFraudalentTransaction(
				Arrays.asList(testTransaction, testTransaction, testTransaction), 40));
	}
	
	@Test
	public void testValidateAllTransactions() throws ParseException {
		Transaction testTransaction1 = new Transaction("10d7ce2f43e35fa57d1bbf8b1a3", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-30T13:15:54"), 10.00);
		Transaction testTransaction2 = new Transaction("10d7ce2f43e35fa57d1bbf8b1a3", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-29T13:15:54"), 10.00);
		Transaction testTransaction3 = new Transaction("10d7ce2f43e35fa57d1bbf8b1a3", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-29T13:15:54"), 10.00);
		Transaction testTransaction4 = new Transaction("10d7ce2f43e35fa57d1bbf8b1b2", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-30T13:15:54"), 10.00);
		Transaction testTransaction5 = new Transaction("10d7ce2f43e35fa57d1bbf8b1b2", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-29T13:15:54"), 10.00);
		Transaction testTransaction6 = new Transaction("10d7ce2f43e35fa57d1bbf8b1c5", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-29T13:15:54"), 15.00);
		
		Map<String, List<Transaction>> transactionMap = new HashMap<String, List<Transaction>>();
		
		transactionMap.put("10d7ce2f43e35fa57d1bbf8b1a3", Arrays.asList(testTransaction1, testTransaction2, testTransaction3));
		transactionMap.put("10d7ce2f43e35fa57d1bbf8b1b2", Arrays.asList(testTransaction4, testTransaction5));
		transactionMap.put("10d7ce2f43e35fa57d1bbf8b1c5", Arrays.asList(testTransaction6));
		
		assertTrue(3 == new ValidationHelper().validateAndGetFraudalentCards(transactionMap, 5).size());
		assertTrue(2 == new ValidationHelper().validateAndGetFraudalentCards(transactionMap, 10).size());
		assertTrue(1 == new ValidationHelper().validateAndGetFraudalentCards(transactionMap, 15).size());
		assertTrue(new ValidationHelper().validateAndGetFraudalentCards(transactionMap, 20).isEmpty());
	}
	
}
