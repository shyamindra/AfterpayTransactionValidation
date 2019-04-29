package com.afterpay.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;

import com.afterpay.data.CSVDataHelper;
import com.afterpay.data.Transaction;

public class ValidationHelper {
	
	Logger logger = LoggerFactory.getLogger(CSVDataHelper.class);

	public List<String> validateAndGetFraudalentCards(Map<String, List<Transaction>> transactionMap, double fraudLimit){
		List<String> fraudalentList = new ArrayList<String>();
		for (Map.Entry<String,List<Transaction>> entry : transactionMap.entrySet()) {
			if(isFraudalentTransaction(entry.getValue(), fraudLimit)) {
				if(!fraudalentList.contains(entry.getKey())){
					fraudalentList.add(entry.getKey());
				}
			}
		}
		logger.debug("fraudalentList" + fraudalentList.toString());
		return fraudalentList;
	}
	
	public boolean isFraudalentTransaction(List<Transaction> transactionList, double fraudLimit) {
		try {
			Map<Object, List<Transaction>> transactionsByDay = transactionList.stream()
			        .collect(Collectors.groupingBy(d -> d.getDate()));
			logger.debug("transactionsByDay:" + transactionsByDay.toString());
			for (Map.Entry<Object,List<Transaction>> entry : transactionsByDay.entrySet()) {
				logger.debug("Sum:" + entry.getValue().stream().mapToDouble(i -> i.getAmount()).sum());
				return (entry.getValue().stream().mapToDouble(i -> i.getAmount()).sum() > fraudLimit);
			}
		} catch (ParseException pe) {
			logger.error(pe.getMessage());
		}
		return false;
	}
}
