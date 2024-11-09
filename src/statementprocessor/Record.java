// Record.java
package statementprocessor;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a financial record containing information about a specific transaction.
 * This class encapsulates details such as the transaction reference, account number,
 * description, start balance, mutation (change in balance), and end balance.
 * Since we are talking about financial records we are using the Java BigDecimal type,
 * this type provides precise handling of decimal values without the rounding errors
 * associated with floating-point types like float or double.
 * <p>
 * Instances of this class are used to store transaction data that can be read from
 * CSV or XML files and processed further for validation and reporting.
 * </p>
 */
public class Record {
    private final int transactionReference;
    private final String accountNumber;
    private final BigDecimal startBalance;
    private final char mutationType;
    private final BigDecimal mutationAmount;
    private final String description;
    private final BigDecimal endBalance;

    public Record(int transactionReference, String accountNumber, String description, BigDecimal startBalance,
                  String mutation,  BigDecimal endBalance) {
        if (!isValidFormat(mutation)) {
            throw new IllegalArgumentException("Invalid value format: " + mutation);
        }
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumber;
        this.startBalance = startBalance;
        this.mutationType = mutation.charAt(0);
        this.mutationAmount = new BigDecimal(mutation.substring(1));

        this.description = description;
        this.endBalance = endBalance;
    }

    public int getTransactionReference() { return transactionReference; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getStartBalance() { return startBalance; }
    public char getMutationType() { return mutationType; }
    public BigDecimal getMutationAmount() { return mutationAmount; }
    public String getDescription() { return description; }
    public BigDecimal getEndBalance() { return endBalance; }

    private boolean isValidFormat(String format) {
        // Regular expression to match values like "+99.33" or "-12.00"
        String regex = "^[+-]?\\d+(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(format);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return "Transaction Reference: " + transactionReference + ", Description: " + description;
    }
}
