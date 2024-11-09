// ReportGeneratorTest.java
package statementprocessor;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ReportGeneratorTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testGenerateReport() {
        // Create sample failed records
        Record record1 = new Record(101, "NL91ABNA0417164300","Deposit", new BigDecimal("500.0"), "+50",  new BigDecimal("550.0"));
        Record record2 = new Record(102, "NL91ABNA0417164301","Withdrawal Error",  new BigDecimal("300.0"), "-100", new BigDecimal("200.0"));
        Record record3 = new Record(103, "NL91ABNA0417164302", "Balance Mismatch",new BigDecimal("100.0"), "-50",  new BigDecimal("50.0"));

        // Create a list of failed records
        List<Record> failedRecords = Arrays.asList(record1, record2, record3);

        // Generate the report
        ReportGenerator.generateReport(failedRecords);

        // Expected output
        String expectedReport = "Failed Records Report:\n" +
                "Transaction Reference: 101, Description: Deposit\n" +
                "Transaction Reference: 102, Description: Withdrawal Error\n" +
                "Transaction Reference: 103, Description: Balance Mismatch\n";

        // Assert that the generated report matches the expected output
        assertEquals("The generated report does not match the expected output", expectedReport.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGenerateReportWithEmptyList() {
        // Test with an empty list of failed records
        List<Record> failedRecords = List.of();

        // Generate the report
        ReportGenerator.generateReport(failedRecords);

        // Expected output for an empty report
        String expectedReport = "Failed Records Report:\nNo failed records found.";

        // Assert that the generated report matches the expected output for an empty list
        assertEquals("The generated report for an empty list does not match the expected output", expectedReport.trim(), outputStreamCaptor.toString().trim());
    }
}
