// FileReaderUtilTest.java
package statementprocessor;

import org.junit.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

public class FileReaderUtilTest {

    @Test
    public void testReadCSV() throws IOException {
        List<Record> records = FileReaderUtil.readCSV("test/resources/records.csv");
        assertEquals("CSV records count mismatch", 10, records.size());
    }

    @Test
    public void testReadXML() throws Exception {
        List<Record> records = FileReaderUtil.readXML("test/resources/records.xml");
        assertEquals("XML records count mismatch", 10, records.size());
    }

}
