// FileReaderUtil.java
package statementprocessor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Utility class for reading different file formats (CSV and XML) and converting them
 * into a list of {@link Record} objects. The class contains methods to read files,
 * parse the content, and extract the necessary data fields to create {@link Record} objects.
 * <p>
 * The {@link FileReaderUtil} class currently supports reading CSV files where the fields
 * are expected to follow a predefined order, and XML files where records are represented
 * by <code>&lt;record&gt;</code> elements.
 * </p>
 */
public class FileReaderUtil {
    /**
     * Reads a CSV file and converts each row into a {@link Record} object.
     * The CSV is expected to have six columns representing the following fields:
     * transactionReference, accountNumber, description, startBalance, mutation, and endBalance.
     * <p>
     * The method skips the header row and processes the remaining rows.
     * </p>
     *
     * @param filePath the file path of the CSV file to read
     * @return a list of {@link Record} objects containing the data from the CSV file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws NumberFormatException if there is an error parsing numerical values from the CSV
     */
    public static ArrayList<Record> readCSV(String filePath) throws IOException, NumberFormatException {
        // TODO: this file is now assuming a standard order of the columns in the csv, it should probably use another java library that works with headers (e.g. apache commons)
        // TODO: Some validation on what headers should exits should be expanded (now we only check line length)
        ArrayList<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;  // Flag to check the first line
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (isFirstLine) {
                    isFirstLine = false;  // After the first line, set the flag to false
                    continue;  // Skip the rest of the loop for the header line
                }
                String[] fields = line.split(",");
                if (fields.length == 6) {
                    Record record = new Record(
                            Integer.parseInt(fields[0]),
                            fields[1],
                            fields[2],
                            new BigDecimal(fields[3]),
                            fields[4],
                            new BigDecimal(fields[5])
                    );
                    records.add(record);
                }
            }
        }
        return records;
    }

    /**
     * Reads an XML file and converts each <code>&lt;record&gt;</code> element into a {@link Record} object.
     * The XML is expected to have the following structure:
     * <pre>
     * &lt;record reference="178792"&gt;
     *     &lt;accountNumber&gt;NL69ABNA0433647324&lt;/accountNumber&gt;
     *     &lt;description&gt;Flowers from Rik Dekker&lt;/description&gt;
     *     &lt;startBalance&gt;51.99&lt;/startBalance&gt;
     *     &lt;mutation&gt;+16.97&lt;/mutation&gt;
     *     &lt;endBalance&gt;68.96&lt;/endBalance&gt;
     * &lt;/record&gt;
     * </pre>
     *
     * @param filePath the file path of the XML file to read
     * @return a list of {@link Record} objects containing the data from the XML file
     * @throws ParserConfigurationException if a document builder cannot be created
     * @throws IOException if an I/O error occurs while reading the file
     * @throws SAXException if an error occurs while parsing the XML
     * @throws NumberFormatException if there is an error parsing numerical values from the XML
     */
    public static ArrayList<Record> readXML(String filePath) throws ParserConfigurationException, IOException, SAXException, NumberFormatException {
        ArrayList<Record> records = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File(filePath));

        // Making sure the XML document is "correctly" formatted
        doc.getDocumentElement().normalize();
        // TODO: maybe some validation on the XML format could be added with e.g. XSD
        NodeList nodeList = doc.getElementsByTagName("record");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i);
                Record record = new Record(
                        Integer.parseInt(element.getAttribute("reference")),
                        element.getElementsByTagName("accountNumber").item(0).getTextContent(),
                        element.getElementsByTagName("description").item(0).getTextContent(),
                        new BigDecimal(element.getElementsByTagName("startBalance").item(0).getTextContent()),
                        element.getElementsByTagName("mutation").item(0).getTextContent(),
                        new BigDecimal(element.getElementsByTagName("endBalance").item(0).getTextContent())
                );
                records.add(record);
            }
        }
        return records;
    }
}
