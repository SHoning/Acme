// ReportGenerator.java
package statementprocessor;

import java.util.List;

/**
 * This class is responsible for generating a report of failed records.
 * It prints the failed records to the standard output. If there are no failed records,
 * it prints a message indicating that no failed records were found.
 */
public class ReportGenerator {

    /**
     * Generates a report of the failed records.
     *
     * <p>If the provided list of failed records is empty, it prints a message indicating that no failed records
     * were found. Otherwise, it iterates over the list and prints each failed record.</p>
     *
     * @param failedRecords the list of records that failed validation
     */
    public static void generateReport(List<Record> failedRecords) {
        // TODO: could easily be expanded to write the results to a location
        // TODO: reason for failure would be a nice addition to this project, but requires some extra toughts as to
        //       where stuff would need to be added.
        System.out.println("Failed Records Report:");
        if (failedRecords.isEmpty()) {
            System.out.println("No failed records found.");
            return;
        }
        for (Record record : failedRecords) {
            System.out.println(record);
        }
    }
}
