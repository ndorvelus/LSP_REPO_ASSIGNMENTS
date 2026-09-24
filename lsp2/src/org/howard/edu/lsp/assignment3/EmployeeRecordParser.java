package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.util.Optional;

public final class EmployeeRecordParser {

    private static final int EXPECTED_FIELD_COUNT = 5;

    public Optional<RawEmployeeData> parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return Optional.empty();
        }

        String[] rawFields = line.split(",", -1);
        if (rawFields.length != EXPECTED_FIELD_COUNT) {
            return Optional.empty();
        }

        String employeeIdStr = rawFields[0].trim();
        String name = rawFields[1].trim().toUpperCase();
        String department = rawFields[2].trim();
        String hoursWorkedStr = rawFields[3].trim();
        String hourlyRateStr = rawFields[4].trim();

        Integer employeeId = parseInteger(employeeIdStr);
        if (employeeId == null) {
            return Optional.empty();
        }

        BigDecimal hoursWorked = parseDecimal(hoursWorkedStr);
        if (hoursWorked == null || hoursWorked.compareTo(BigDecimal.ZERO) < 0) {
            return Optional.empty();
        }

        BigDecimal hourlyRate = parseDecimal(hourlyRateStr);
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) < 0) {
            return Optional.empty();
        }

        return Optional.of(new RawEmployeeData(employeeId, name, department, hoursWorked, hourlyRate));
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}