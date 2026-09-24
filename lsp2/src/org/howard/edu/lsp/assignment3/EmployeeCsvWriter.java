package org.howard.edu.lsp.assignment3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class EmployeeCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    private final String outputPath;

    public EmployeeCsvWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    public void write(List<Employee> employees) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println(HEADER);
            for (Employee employee : employees) {
                writer.println(employee.toCsvRow());
            }
        }
    }
}