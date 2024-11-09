// RecordTest.java
package statementprocessor;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RecordTest {

    @Test
    public void testIsValidFormat() {
        assertThrows(IllegalArgumentException.class, () ->{
                new Record(123, "NL91ABNA0417164300", "Deposit",new BigDecimal("1000.0"), "a+50",  new BigDecimal("1050.0"));
        });
    }
}
