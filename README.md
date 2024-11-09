# Customer Statement Processor

## Overview

The **Customer Statement Processor** is a Java application developed for **Acme Inc.** to process monthly customer statement records. 
The application reads customer records in CSV or XML format, validates them, and generates a report for any failed records. 
The validation ensures:
1. Each transaction reference is unique.
2. The end balance is validated based on the start balance and mutation.

---

## Project Structure

The project is structured into several classes to handle different aspects of statement processing:

- **Record**: The Model class that represents a single customer record.
- **Validator**: Handles validation checks for records, ensuring uniqueness of transaction references and accurate end balances.
- **ReportGenerator**: Generates a report listing failed records after validation.
- **CustomerStatementProcessor**: Main class that loads records, initiates validation, and triggers report generation.
- **FileReaderUtil (CSVParser and XMLParser)**: Provides methods for parsing records from CSV and XML input files, respectively.

---

## Requirements

- **Java 8 or higher**
- **JUnit 4** for unit testing
- **Intellij** [Download the community edition here at the bottom](https://www.jetbrains.com/idea/download/?section=mac)

---

## Setup Instructions

To run and add tot this application I have made it as easy as possible. I have set it up with the Intellij compiler and
kept all the added libraries (junit and hamcrest) in the git project so that it is easy to run quickly.
It should be ready to just press play once you have cloned and opened the project in Intellij.

## Cloning the Repository

Clone the project repository to your local machine using the following command:

```bash
git clone https://github.com/yourusername/projectname.git
```

## Importing the Project into IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **Open** from the welcome screen and choose the project folder you just cloned.

---

## Usage

1. **Run the Application**:
   - The main class is `StatementProcessor`. You can run it directly in IntelliJ or from the command line.

2. **Input Data**:
   - Place your CSV or XML files anywhere and use that path (example files can be found in `/test/resources`).

3. **Output Report**:
   - The application will produce a report in the console, listing all failed records with their transaction references and descriptions.

---

### Notes

- The application currently supports only a simplified version of the MT940 format in CSV and XML.
- Assumption is now done that files are indeed the type of file that their extension mentions. (e.g. a csv file is indeed saved as .csv and doesn't contain XML data)
- Error handling and logging can be enhanced for better production use.
- Some improvement TODO's are left in the code to show what could be worked on when more effort would be added to the project
- Packaging and package management are probably better left to e.g. Maven in production environments.
- Sorry for all people looking in github hoping to see the different steps I took and just see a single commit. 
  This is not how I think it should be done, I shall try to do it better next time ;)
