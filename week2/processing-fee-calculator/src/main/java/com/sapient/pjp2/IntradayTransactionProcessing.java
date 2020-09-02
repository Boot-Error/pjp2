package com.sapient.pjp2;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * This class implements a TransactionProcessor based on the following rules of processing:
 *
 * Intraday transactions: Intra-day transactions are the ones where security
 * (like IBM Equity) is bought and sold on the same day.
 *
 * Intra-day transactions will have two transactions having same Client Id, Security Id, 
 * and Transaction Date but opposite Transaction Type i.e.
 * one transaction would be ‘Sell’ ‘ and other would be ‘Buy’. Each ‘intra-day
 * transaction should be charged $10 for both the Buy and Sell legs
 *
 */
class IntradayTransactionProcessing
implements TransactionProcessor
{

	private float intraDayCharePerTransaction = 10;
	List<ProcessingFeeResult> pfrs;

	public IntradayTransactionProcessing()
	{
		this.pfrs = new ArrayList<ProcessingFeeResult>();
	}

	public void updateProcessFeeResultList(List<ProcessingFeeResult> pfrs)
	{
		this.pfrs.addAll(pfrs);
	}

	public List<ProcessingFeeResult> processTransactions(List<Transaction> transactions)
	{
		List<Transaction> buyTransactions = transactions
													.stream()
													.filter(tr -> tr.transactionType == Transaction.TransactionType.BUY)
													.collect(Collectors.toList());

		List<Transaction> sellTransactions = transactions
													.stream()
													.filter(tr -> tr.transactionType == Transaction.TransactionType.SELL)
													.collect(Collectors.toList());

		for (Transaction bTr: buyTransactions)
		{
			for (Transaction sTr: sellTransactions)
			{
				if (bTr.clientId == sTr.clientId && bTr.securityId == sTr.securityId && bTr.transactionDate == sTr.transactionDate) {
					System.out.println("this is a intraday transaction");
					ProcessingFeeResult pfr = new ProcessingFeeResult(
													bTr,
													intraDayCharePerTransaction * 2);

					pfr.addOrCombine(this.pfrs);
				}
			}
		}

		return pfrs;
	}
}
