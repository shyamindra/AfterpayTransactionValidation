package com.afterpay.transactionvalidation;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.afterpay.data.CSVDataHelper;
import com.afterpay.validation.ValidationHelper;

@SpringBootApplication
public class TransactionValidationApp {

	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(TransactionValidationApp.class);
		
		try {
			CSVDataHelper csvDataHelper = new CSVDataHelper();
			csvDataHelper.writeListToFile( 
					new ValidationHelper().validateAndGetFraudalentCards(
							csvDataHelper.readTransactionData(
								new File("src/main/resources/TransactionData.csv")), 20), 
								new File("src/main/resources/FraudalentData.csv"));
			logger.debug("Transactions from CSV:" + new CSVDataHelper().readTransactionData(
					new File("src/main/resources/TransactionData.csv")));
		} catch (NumberFormatException | FileNotFoundException | ParseException e) {
			logger.error(e.getMessage());
		}
	}
}
