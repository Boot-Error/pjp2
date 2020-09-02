package com.sapient.pjp2;

import java.util.Date;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * The ProcessingFeeResult class represnts the data produced by processing the transactions
 * and computing the processing fee.
 *
 * It also involves methods regarding updating a container of ProcessingFeeResult and various
 * formatters
 *
 * @see TransactionProcessor
 *
 */
class ProcessingFeeResult
{
	public String clientId;
	public String transactionType, priorityFlag;
	public Date transactionDate; 
	public Float processingFee;

	public ProcessingFeeResult(
			String clientId,
			Transaction.TransactionType transactionType,
			Date transactionDate,
			Transaction.PriorityFlagType priority,
			Float processingFee)
	{
		this.clientId = clientId;
		this.transactionType = transactionType.toString();
		this.transactionDate = transactionDate;
		this.priorityFlag = priority.toString();
		this.processingFee = processingFee;
	}

	/**
	 * Construct ProcessingFeeResult from a Transaction.
	 *
	 * @param Transaction A Transaction
	 * @param fee The Processing Fee to be set
	 *
	 * @see Transaction
	 */
	public ProcessingFeeResult(Transaction tr, float fee)
	{
		this.clientId = tr.clientId;
		this.transactionType = tr.transactionType.toString();
		this.transactionDate = tr.transactionDate;
		this.priorityFlag = tr.priorityFlag.toString();
		this.processingFee = fee;
	}

	/**
	 * Converts a ProcessingFeeResult to a row to be written in csv file.
	 *
	 * The row is demited by ','
	 * The row is of this format:
	 * 		clientId,transactionType,transactionDate,priorityFlag,processingFee
	 *
	 */
	public String getCSV()
	{
		StringBuilder row = new StringBuilder();

		row.append(this.clientId);
		row.append(",");
		row.append(this.transactionType);
		row.append(",");
		row.append(this.getDateStr(this.transactionDate));
		row.append(",");
		row.append(this.priorityFlag);
		row.append(",");
		row.append(this.processingFee);

		return row.toString();
	}

	/**
	 * Formats a Date object to dd/MM/yyyy string to be written in csv file
	 */
	public String getDateStr(Date d)
	{
		SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy");
		String dStr = null;

		dStr = dft.format(d);
		
		return dStr;

	}

	/**
	 * Matches 2 ProcessingFeeResult based on all parameters except the
	 * processingFee.
	 *
	 * This is used in addOrCombine and various other places to match and 
	 * update ProcessingFeeResult
	 *
	 * @param ProcessingFeeResult another ProcessingFeeResult
	 * 
	 * @return boolean If equal then true else false
	 *
	 */
	public boolean equalsExceptFee(ProcessingFeeResult other)
	{
		if (this.clientId == other.clientId 
				&& this.transactionDate == other.transactionDate 
				&& this.transactionType == other.transactionType 
				&& this.priorityFlag == other.priorityFlag)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * This method combines 2 equalsExceptFee ProcessingFeeResult into one
	 * by adding the processingFee of 2 ProcessingFeeResult.
	 *
	 * @param ProcessingFeeResult another ProcessingFeeResult
	 *
	 * @return boolean If combined then true else false
	 *
	 */
	public boolean combine(ProcessingFeeResult other)
	{

		if (this.equalsExceptFee(other)) {
			this.processingFee += other.processingFee;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method takes a List of ProcessingFeeResult and adds this object to it,
	 * if the Object already exists which is found by equalsExceptFee() then the object 
	 * in the list is combined and replaced with this object.
	 *
	 * This is used by TransactionProcessor to process in series by updating a List of ProcessingFeeResult
	 *
	 * @param List<ProcessingFeeResult> List of ProcessingFeeResult
	 *
	 * @return List<ProcessingFeeResult> Updated list of ProcessingFeeResult
	 *
	 */
	public List<ProcessingFeeResult> addOrCombine(List<ProcessingFeeResult> pfrs)
	{

		boolean isCombined = false;
		for (int i = 0; i < pfrs.size(); i++)
		{
			ProcessingFeeResult pfr = pfrs.get(i);
			if (this.equalsExceptFee(pfr))
			{
				this.combine(pfr);
				pfrs.set(i, this);
				isCombined = true;
			}
		}

		if (!isCombined)
		{
			pfrs.add(this);
		}

		return pfrs;
		
	}
}
