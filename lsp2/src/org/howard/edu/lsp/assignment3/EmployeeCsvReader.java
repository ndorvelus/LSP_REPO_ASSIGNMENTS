package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EmployeeCsvReader {

    private final String inputPath;

    public EmployeeCsvReader(String inputPath) {
        this.inputPath = inputPath;
    }

    public List<String> readDataLines() throws IOException {
        List<String> dataLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line = reader.readLine(); // header row - discard
            if (line == null) {
                return Collections.emptyList();
            }

            while ((line = reader.readLine()) != null) {
                dataLines.add(line);
            }
        }

        return dataLines;
    }
}