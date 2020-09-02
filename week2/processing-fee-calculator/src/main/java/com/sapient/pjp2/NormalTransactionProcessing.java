package com.sapient.pjp2;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements a TransactionProcessor based on the following rules of processing:
 *
 * Normal Transaction Processing: A nominal fee is charge based on the following
 * rules.
 *
 * - $500 for a transaction with high priority (denoted by the priority field in
 * the transaction) 
 * - $100 for a transaction with normal priority and Transaction Type is Sell and Withdraw 
 * - $50 for a transaction with normal priority and Transaction Type Code is Buy and Deposit $50 for a
 * transaction with normal priority and Transaction Type Code is Buy and Deposit.
 */
class NormalTransactionProcessing
implements TransactionProcessor
{

	List<ProcessingFeeResult> pfrs;

	public NormalTransactionProcessing()
	{
		this.pfrs = new ArrayList<ProcessingFeeResult>();
	}

	public void updateProcessFeeResultList(List<ProcessingFeeResult> pfrs)
	{
		this.pfrs.addAll(pfrs);
	}

	public List<ProcessingFeeResult> processTransactions(List<Transaction> transactions)
	{

		// charge 500 for high priorty transactions
		transactions.stream()
			.filter(tr -> tr.priorityFlag == Transaction.PriorityFlagType.Y)
			.map(tr -> new ProcessingFeeResult(tr, 500))
			.forEach(pfr -> pfr.addOrCombine(this.pfrs));

		// charge 100 for normal priority with TransactionType SELL or WITHDRAW
		transactions.stream()
			.filter(tr -> tr.priorityFlag == Transaction.PriorityFlagType.N 
						&& (tr.transactionType == Transaction.TransactionType.SELL
						|| tr.transactionType == Transaction.TransactionType.WITHDRAW))
			.map(tr -> new ProcessingFeeResult(tr, 100))
			.forEach(pfr -> pfr.addOrCombine(this.pfrs));

		// charge 50 normal transaction with normal priority and TransactionType as Buy and sell
		transactions.stream()
			.filter(tr -> tr.priorityFlag == Transaction.PriorityFlagType.N 
						&& (tr.transactionType == Transaction.TransactionType.BUY
						|| tr.transactionType == Transaction.TransactionType.DEPOSIT))
			.map(tr -> new ProcessingFeeResult(tr, 50))
			.forEach(pfr -> pfr.addOrCombine(this.pfrs));

		return pfrs;

	}

}
