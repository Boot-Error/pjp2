package com.sapient.pjp2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * The ProcessingFeeCalculator class has methods which
 * - reads a csv file to build list of transactions
 * - processess the transactions computing the processing fees
 * - write the processing fee results into a csv file
 * 
 * @see Transaction
 * @see ProcessingFeeResult
 * @see TransactionProcessor
 *
 */
class ProcessingFeeCalculator
{

	LinkedList<Transaction> transactions;
	List<ProcessingFeeResult> processFeeResults;
	ArrayList<TransactionProcessor> trProcs;

	public ProcessingFeeCalculator()
	{
		System.out.println( "Initializing ProcessingFeeCalculator" );
		this.transactions = new LinkedList<Transaction>();
		this.processFeeResults = new ArrayList<ProcessingFeeResult>();

		this.trProcs = new ArrayList<TransactionProcessor>();

		// add 2 Transaction Processors
		this.trProcs.add(new IntradayTransactionProcessing());
		this.trProcs.add(new NormalTransactionProcessing());
	}

	/**
	 * This method reads a csv file with 7 columns containing values on Transactions.
	 *
	 * @param filename
	 * 			name of the file
	 * 
	 * @throws FileNotFoundException If the file is not at the filepath supplied.
	 *
	 */
	public void readTransactions(String filename)
	{

		// open the file for reading
		File trFile = null;
		Scanner sc = null;

		try
		{
			trFile = new File(filename);
			sc = new Scanner(trFile);
			sc.useDelimiter("\n");

			Integer rowCount = 0;

			while (sc.hasNext()) {
				String row = sc.next();
				String[] values = row.split(",");

				// a transaction has 6 fields
				if (values.length != 7) {
					System.out.println("Invalid row " + row + " skipping..");
				}

				// create instance
				Transaction tr = new Transaction(
						values[0],
						values[1],
						values[2],
						values[3],
						values[4],
						values[5],
						values[6]);

				this.transactions.add(tr);
				rowCount += 1;
			}

			System.out.println("Read " + rowCount.toString() + " rows..");

		} catch(FileNotFoundException e) 
		{
			System.out.println("File not found exiting..");
			System.exit(1);

		} finally 
		{
			if (sc != null) {
				sc.close();
			}
		}
		
	}

	/**
	 * Shows list of transactions read from the csv file
	 *
	 * @see Transaction
	 * 
	 */
	public void showTransactions()
	{
		for (Transaction tr: this.transactions)
		{
			tr.show();
		}
	}

	/**
	 * This method processes the transactions using the TransactionProcessor available
	 *
	 */
	public void processTransactions()
	{
		for(TransactionProcessor trProc: trProcs)
		{
			trProc.updateProcessFeeResultList(this.processFeeResults);
			this.processFeeResults = trProc.processTransactions(transactions);
		}
	}

	/**
	 * This method writes the ProcessingFeeResult to a csv file.
	 *
	 * Overwrites if the file exists or creates a new file, if no file exists.
	 *
	 * @param filename name of the file
	 *
	 * @see processFeeResults
	 *
	 */
	public void writeResults(String filename)
	{
		File pfrFile = null;
		FileWriter pfrFw = null;

		try
		{
			pfrFile = new File(filename);
			pfrFw = new FileWriter(pfrFile);

			if (!pfrFile.exists())
			{
				pfrFile.createNewFile();
			}

			for (ProcessingFeeResult pfr: this.processFeeResults)
			{
				pfrFw.write(pfr.getCSV());
				pfrFw.write("\n");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		finally
		{
			try
			{
				if (pfrFw != null)
				{
					pfrFw.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}

	}
	
}
