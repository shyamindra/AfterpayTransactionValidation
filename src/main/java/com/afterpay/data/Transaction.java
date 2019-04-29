package com.afterpay.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction {
	
	Logger logger = LoggerFactory.getLogger(Transaction.class);
	
	private String creditCardNumber;
	private Date timestamp;
	private double amount;
	
	public Transaction(String creditCardNumber, Date timestamp, double amount) {
		this.setCreditCardNumber(creditCardNumber);
		this.setTimestamp(timestamp);
		this.setAmount(amount);
	}
	
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timstamp) {
		this.timestamp = timstamp;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String toString() {
		return this.getCreditCardNumber() + ":" + this.getTimestamp() + ":" + this.getAmount();
	}
	public Date getDate() {
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date =  formatter.parse(formatter.format(this.getTimestamp()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;
	}
	public boolean equals(Transaction transaction) {
		return this.getCreditCardNumber().equals(transaction.getCreditCardNumber()) &&
				this.getTimestamp().equals(transaction.getTimestamp()) &&
				this.getAmount() == transaction.getAmount();
	}
	
}
