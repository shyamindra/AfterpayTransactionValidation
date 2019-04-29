package com.afterpay.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CSVDataHelper {
	
	Logger logger = LoggerFactory.getLogger(CSVDataHelper.class);
	
	public Map<String, List<Transaction>> readTransactionData(File file) throws FileNotFoundException, NumberFormatException, ParseException{
		
		Map<String, List<Transaction>> transactionMap = new HashMap<String, List<Transaction>>();
		Scanner scan = new Scanner(file);
		
		while(scan.hasNext()) {
	        String[] values = scan.nextLine().split(",");
	        Transaction transaction = new Transaction(values[0], 
	        				fetchDate(values[1]), Double.parseDouble(values[2]));
	        addTransactionToMap(transactionMap, transaction);
		}
		logger.debug("transactionMap:" + transactionMap.toString());
		scan.close();
		return transactionMap;
	}
	
	public void writeListToFile(List<String> fraudalentList, File file) throws FileNotFoundException {
	    PrintWriter printWriter = new PrintWriter(file);
	    fraudalentList.forEach(s -> printWriter.println(s));
	    printWriter.close();
	}

	private void addTransactionToMap(Map<String, List<Transaction>> transactionMap, Transaction transaction) {
		
		List<Transaction> transactionList = transactionMap.get(transaction.getCreditCardNumber());
		if(transactionList == null) {
			transactionList = new ArrayList<Transaction>();
		}
		transactionList.add(transaction);
		transactionMap.put(transaction.getCreditCardNumber(), transactionList);
		
	}
	
	private Date fetchDate(String timeStamp) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(timeStamp);
	}

}
