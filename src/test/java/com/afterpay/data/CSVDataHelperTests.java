package com.afterpay.data;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import com.afterpay.data.CSVDataHelper;
import com.afterpay.data.Transaction;

public class CSVDataHelperTests {

	@Test
	public void testInputFileExists() {
		File file = new File("data/TransactionData.csv");
		assertTrue(file.exists());
	}
	
	@Test
	public void testOutputFileCreation() throws FileNotFoundException {
		File file = new File("data/TestFraudalentData.csv");
		new CSVDataHelper().writeListToFile(Arrays.asList("testHashCreditCard"), file);
		assertTrue(file.exists());
	}
	
	@Test
	public void testOutputFileData() throws FileNotFoundException {
		File file = new File("data/TestFraudalentData.csv");
		new CSVDataHelper().writeListToFile(Arrays.asList("testHashCreditCard"), file);
		Scanner scan = new Scanner(file);
		assertTrue(scan.hasNextLine());
		assertTrue(scan.nextLine().equalsIgnoreCase("testHashCreditCard"));
		scan.close();
	}
	
	@Test
	public void testInputFileData() throws NumberFormatException, FileNotFoundException, ParseException {
		File file = new File("data/TestTransactionData.csv");
		PrintWriter printWriter = new PrintWriter(file);
	    printWriter.println("10d7ce2f43e35fa57d1bbf8b1a3, 2014-04-30T13:15:54, 10.00");
	    printWriter.close();
		new CSVDataHelper().readTransactionData(file);
		List<Transaction> transactions = Arrays.asList(new Transaction("10d7ce2f43e35fa57d1bbf8b1a3", 
				new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse("2014-04-30T13:15:54"), 10.00));
		List<Transaction> inputTransactions = new CSVDataHelper().readTransactionData(file).get("10d7ce2f43e35fa57d1bbf8b1a3");
		assertTrue(transactions.get(0).equals(inputTransactions.get(0)));
	}
	
}
