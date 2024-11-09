// Validator.java
package statementprocessor;

import java.math.BigDecimal;
import java.util.*;

/**
 * The {@code Validator} class provides methods for validating a list of {@code Record} objects.
 * It ensures that each record has a unique transaction reference and that the calculated end balance
 * matches the reported end balance based on the start balance, mutation type and mutation amount.
 */
public class Validator {
    /**
     * Validates a list of records by checking the uniqueness of transaction references and
     * ensuring that the mutation results in the correct end balance.
     *
     * @param records A list of {@code Record} objects to be validated.
     * @return A list of {@code Record} objects that have failed validation, i.e., either
     *         containing duplicate transaction references or incorrect end balances.
     */
    public static ArrayList<Record> validateRecords(List<Record> records) {
        // TODO: split up the validations into separate methods, this makes it more readable/extensible
        //   when adding more validations later
        Set<Integer> uniqueReferences = new HashSet<>();
        ArrayList<Record> failedRecords = new ArrayList<>();

        for (Record record : records) {
            if (!uniqueReferences.add(record.getTransactionReference())) {
                failedRecords.add(record);
                continue;
            }

            BigDecimal calculatedEndBalance = record.getStartBalance();
            if (record.getMutationType() == '+') {
                calculatedEndBalance = calculatedEndBalance.add(record.getMutationAmount());
            } else if (record.getMutationType() == '-') {
                calculatedEndBalance = calculatedEndBalance.subtract(record.getMutationAmount());
            }

            if (!calculatedEndBalance.equals(record.getEndBalance())) {
                failedRecords.add(record);
            }
        }
        return failedRecords;
    }
}
