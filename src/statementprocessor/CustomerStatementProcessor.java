// CustomerStatementProcessor.java
package statementprocessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for processing customer statement files.
 * It supports reading both CSV and XML file formats, validating records,
 * and generating a report for failed records.
 *
 * The main method determines the file type (CSV or XML) based on the file extension,
 * reads the data from the provided file, validates the records, and generates a report.
 */
public class CustomerStatementProcessor {
    /**
     * The main method that serves as the entry point of the application.
     * It handles file input (either through command line arguments or interactively),
     * determines the file type, processes the file, validates the records,
     * and generates a report for failed records.
     *
     * @param args command line arguments. The optional first argument should be the file path
     *             (either a CSV or XML file).
     */
    public static void main(String[] args) {
        String filePath;
        if (args.length != 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the path of the CSV or XML file you want to process: ");
            filePath = scanner.nextLine();
        } else {
            filePath = args[0];
        }

        try {
            ArrayList<Record> records;

            if (filePath.endsWith(".csv")) {
                records = FileReaderUtil.readCSV(filePath);
            } else if (filePath.endsWith(".xml")) {
                records = FileReaderUtil.readXML(filePath);
            } else {
                System.out.println("Unsupported file type. Please provide a CSV or XML file.");
                return;
            }

            ArrayList<Record> failedRecords = Validator.validateRecords(records);
            ReportGenerator.generateReport(failedRecords);

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file could not be found at the specified path. Please check the file path and try again.");
        } catch (AccessDeniedException e) {
            System.out.println("Error: You do not have permission to access the file at the specified path. Please check your file permissions or try running the program with higher privileges.");
        } catch (IOException e) {
            System.out.println("Error: An unexpected I/O error occurred while reading the file. Please try again.");
        } catch (NumberFormatException e) {
            // TODO: make this message more valuable, by identifying for which transactionReference and description it goes wrong
            System.out.println("Error: The file contains an invalid record.");
        } catch (Exception e) {
            System.out.println("Error processing statements: " + e.getMessage());
        }
    }
}
