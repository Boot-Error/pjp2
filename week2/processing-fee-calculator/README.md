# Processing Fee Calculator

This is the week2 assignment on implement a cli application to processes transactions and compute their processing fee based on certain rules.

# Install

This is a Java v14.0.1 project and require Apache Maven 3.6.3.

1. Clone the project
2. `mvn package`

Run with the example transaction provided in [sample_transactions.txt](example/sample_transactions.txt)
```sh
java -cp target/processing-fee-calculator-1.0-SNAPSHOT.jar com.sapient.pjp2.App example/sample_transactions.txt example/sample_processing_fee.txt
```
