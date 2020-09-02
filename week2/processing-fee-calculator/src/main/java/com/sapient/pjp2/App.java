package com.sapient.pjp2;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

		if (args.length < 2) {
			System.out.println("Not enough arguments");
			System.exit(1);
		}

		String inFilename = args[0];
		String outFilename = args[1];

		ProcessingFeeCalculator pfc = new ProcessingFeeCalculator();

		System.out.println("Reading the file: " + inFilename);
		pfc.readTransactions(inFilename);

		System.out.println("Processing the tranactions..");
		pfc.processTransactions();

		System.out.println("Writing the results to: " + outFilename);
		pfc.writeResults(outFilename);
 b
		System.out.println("Done :)");

    }
}
