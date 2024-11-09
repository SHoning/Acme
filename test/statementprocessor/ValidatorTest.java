// ValidatorTest.java
package statementprocessor;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testDuplicateTransactionReference() {
        Record record1 = new Record(123, "NL91ABNA0417164300","Deposit", new BigDecimal("1000.0"), "+50.00", new BigDecimal("1050.00"));
        Record record2 = new Record(123, "NL91ABNA0417164301","Rent", new BigDecimal("1050.0"), "-100.00", new BigDecimal("950.00"));
        List<Record> records = Arrays.asList(record1, record2);

        List<Record> failedRecords = Validator.validateRecords(records);
        assertEquals("Should identify duplicate transaction reference", 1, failedRecords.size());
    }

    @Test
    public void testEndBalanceValidation() {
        Record record1 = new Record(124, "NL91ABNA0417164300", "Deposit", new BigDecimal("1000.00"), "+50.01", new BigDecimal("1050.01"));
        Record record2 = new Record(125, "NL91ABNA0417164301", "Rent", new BigDecimal("1000.00"), "-50", new BigDecimal("950.00"));
        Record record3 = new Record(126, "NL91ABNA0417164302", "Error Balance", new BigDecimal("1000.00"), "-50", new BigDecimal("900.0"));

        List<Record> records = Arrays.asList(record1, record2, record3);
        List<Record> failedRecords = Validator.validateRecords(records);
        assertEquals("Should identify incorrect end balance", 1, failedRecords.size());
        assertEquals("Transaction ref of failed record should match", 126, failedRecords.get(0).getTransactionReference());
    }
}
