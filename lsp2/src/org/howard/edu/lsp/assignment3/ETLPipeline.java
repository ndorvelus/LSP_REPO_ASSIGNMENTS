package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ETLPipeline {

    private static final String INPUT_PATH = "lsp2/data/employees.csv";
    private static final String OUTPUT_PATH = "lsp2/data/transformed_employees.csv";

    public static void main(String[] args) {
        EmployeeCsvReader reader = new EmployeeCsvReader(INPUT_PATH);
        EmployeeRecordParser parser = new EmployeeRecordParser();
        PayrollCalculator calculator = new PayrollCalculator();
        EmployeeCsvWriter writer = new EmployeeCsvWriter(OUTPUT_PATH);

        List<String> dataLines;
        try {
            dataLines = reader.readDataLines();
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;
        List<Employee> transformedEmployees = new ArrayList<>();

        for (String line : dataLines) {
            rowsRead++;

            Optional<RawEmployeeData> validated = parser.parse(line);
            if (validated.isPresent()) {
                transformedEmployees.add(calculator.calculate(validated.get()));
                rowsTransformed++;
            } else {
                rowsSkipped++;
            }
        }

        try {
            writer.write(transformedEmployees);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }

        printSummary(rowsRead, rowsTransformed, rowsSkipped);
    }

    private static void printSummary(int rowsRead, int rowsTransformed, int rowsSkipped) {
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + OUTPUT_PATH);
    }
}