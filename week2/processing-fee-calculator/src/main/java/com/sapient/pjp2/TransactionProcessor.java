package com.sapient.pjp2;

import java.util.List;

/**
 * This interface describes a Transaction Process which process a list of Transaction to ProcessingFeeResult
 *
 * @see Transaction
 * @see ProcessingFeeResult
 */
public interface TransactionProcessor
{
	/**
	 * This method updates the TransactionProcessor's list of ProcessingFeeResult
	 *
	 * @param List<ProcessingFeeResult>
	 */
	public void updateProcessFeeResultList(List<ProcessingFeeResult> pfrs);
	/**
	 * This method processes the Transaction to ProcessingFeeResult
	 *
	 * @param List<Transaction>
	 * @return List<ProcessingFeeResult>
	 */
	public List<ProcessingFeeResult> processTransactions(List<Transaction> trs);
}
