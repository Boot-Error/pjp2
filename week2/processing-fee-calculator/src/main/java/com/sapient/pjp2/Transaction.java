package com.sapient.pjp2;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * This Transaction class represnts the data read from a csv file containing Transactions
 * which is used to compute the ProcessingFeeResult.
 *
 * It involves methods in parsing csv values from string to respective objects and enums
 *
 */
class Transaction
{

	/**
	 * TransactionType Enum describes types of Transaction
	 */
	public enum TransactionType {
		BUY, SELL, DEPOSIT, WITHDRAW
	}

	/**
	 * PriorityFlagType Enum describes Priority of Transaction
	 *
	 * Y mean High Priority
	 * N mean Normal Priority
	 *
	 */
	public enum PriorityFlagType {
		Y, N
	}
	
	public String clientId, externalTransactionId, securityId;
	public Date transactionDate;
	public TransactionType transactionType;
	public Float marketValue;
	public PriorityFlagType priorityFlag;

	public Transaction(String externalTransactionId,
			String clientId,
			String securityId,
			String transactionType,
			String transactionDate,
			String marketValue,
			String priorityFlag) 
	{
		this.externalTransactionId = externalTransactionId;
		this.clientId = clientId;
		this.securityId = securityId;
		this.transactionDate = parseDate(transactionDate);
		this.transactionType = getTransactionType(transactionType);
		this.marketValue = Float.parseFloat(marketValue);
		this.priorityFlag = getPriorityFlagType(priorityFlag);
	}

	/**
	 * Parses a date string like 01/01/2020 to Date Object
	 *
	 * @param String the Date String ( transactionDate )
	 * @return Date
	 *
	 * @throws ParseException If the data string isn't in correct format
	 */
	public Date parseDate(String dateStr)
	{
		SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;

		try
		{
			d = dft.parse(dateStr);
		}
		catch(ParseException e)
		{
			System.out.printf("The date %s is not format dd/MM/yyyy ", dateStr);
		}
		
		return d;
	}

	/**
	 * Prints this Transaction 
	 */
	public void show()
	{
		StringBuilder output = new StringBuilder();
		output.append("Transaction ID: " + this.externalTransactionId);
		output.append("\n");
		output.append("Client ID: " + this.clientId);
		output.append("\n");
		output.append("TransactionType: " + this.transactionType);
		output.append("\n");
		output.append("Transaction Date: " + this.transactionDate);
		output.append("\n");
		output.append("Market Value: " + this.marketValue.toString());
		output.append("\n");
		output.append("Priority Flag: " + this.priorityFlag);
		output.append("\n");

		System.out.println("-----------[ " + this.externalTransactionId + " ]-----------");
		System.out.println(output);

	}

	/**
	 * Translates the TransactionType string to TransactionType Enum
	 *
	 * @param String The TransactionType 
	 * @return TransactionType 
	 */
	public TransactionType getTransactionType(String transactionType)
	{
		switch (transactionType)
		{
			case "BUY":
				return TransactionType.BUY;

			case "SELL":
				return TransactionType.SELL;

			case "WITHDRAW":
				return TransactionType.WITHDRAW;

			case "DEPOSIT":
				return TransactionType.DEPOSIT;

			default:
				return null;
		}
	}

	/**
	 * Translates PriorityFlag string to PriorityFlagType Enum
	 *
	 * @param String The PriorityFlag
	 * @return PriorityFlagType 
	 */
	public PriorityFlagType getPriorityFlagType(String priorityFlag)
	{
		switch(priorityFlag)
		{
			case "Y":
				return PriorityFlagType.Y;
				
			case "N":
				return PriorityFlagType.N;

			default:
				return null; 
		}
	}
}
