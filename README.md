# XML Parser

The XML Parser is a Java program designed to validate XML documents for correct syntax. It checks for proper nesting, matching tags, and overall well-formed structure to ensure that XML documents adhere to the required standards.

## Installation

### Prerequisites

Java Runtime Environment (JRE) 1.8 or higher: Ensure that Java is installed on your system. If it’s not installed, you can download it from the official Oracle website or OpenJDK.

## Steps

### Obtain the Parser.jar File

Download or copy the Parser.jar file to a directory on your computer where you can easily access it.

## Usage

Run the XML Parser using the command line by providing the path to the XML file you want to validate:

```bash
java -jar Parser.jar path/to/your/xmlfile.xml
```

Replace path/to/your/xmlfile.xml with the actual path to the XML file you wish to validate.

### Included Sample Files

The Parser.jar file comes with two sample XML files:

- sample1.xml
- sample2.xml

These sample files are included within the jar for testing purposes. You can use them to test the parser’s functionality.

To validate one of the included sample files, you can run:

```bash
java -jar Parser.jar sample1.xml
```

or

```bash
java -jar Parser.jar sample2.xml

```

**Note**: Ensure that the Parser.jar file and the sample XML files are in the same directory, or provide the full path to the sample files.

### Example

Assuming you have Parser.jar and the sample XML files in the current directory, you can run:

```bash
java -jar Parser.jar sample1.xml
```

**Output:**

- If the XML file has no syntax errors, the program will display:

```bash
================ERROR LOG================
```

- If there are syntax errors, the program will list them under the error log, showing the line number and details of each error.
