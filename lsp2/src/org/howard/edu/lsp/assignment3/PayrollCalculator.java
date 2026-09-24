package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PayrollCalculator {

    private static final BigDecimal REGULAR_HOURS_LIMIT = new BigDecimal("40.00");
    private static final BigDecimal OVERTIME_MULTIPLIER = new BigDecimal("1.5");
    private static final BigDecimal IT_BONUS_MULTIPLIER = new BigDecimal("1.05");
    private static final String IT_DEPARTMENT = "IT";

    private static final BigDecimal FULL_TIME_THRESHOLD = new BigDecimal("30.00");

    private static final BigDecimal LOW_MAX = new BigDecimal("500.00");
    private static final BigDecimal STANDARD_MAX = new BigDecimal("1000.00");
    private static final BigDecimal HIGH_MAX = new BigDecimal("2000.00");

    public Employee calculate(RawEmployeeData raw) {
        BigDecimal grossPay = calculateGrossPay(raw.getHoursWorked(), raw.getHourlyRate(), raw.getDepartment());
        String payLevel = determinePayLevel(grossPay);
        String employmentStatus = determineEmploymentStatus(raw.getHoursWorked());

        return new Employee(
                raw.getEmployeeId(),
                raw.getName(),
                raw.getDepartment(),
                raw.getHoursWorked(),
                raw.getHourlyRate(),
                grossPay,
                payLevel,
                employmentStatus
        );
    }

    private BigDecimal calculateGrossPay(BigDecimal hoursWorked, BigDecimal hourlyRate, String department) {
        BigDecimal grossPay;

        if (hoursWorked.compareTo(REGULAR_HOURS_LIMIT) <= 0) {
            grossPay = hoursWorked.multiply(hourlyRate);
        } else {
            BigDecimal overtimeHours = hoursWorked.subtract(REGULAR_HOURS_LIMIT);
            BigDecimal regularPay = REGULAR_HOURS_LIMIT.multiply(hourlyRate);
            BigDecimal overtimePay = overtimeHours.multiply(hourlyRate).multiply(OVERTIME_MULTIPLIER);
            grossPay = regularPay.add(overtimePay);
        }

        if (IT_DEPARTMENT.equals(department)) {
            grossPay = grossPay.multiply(IT_BONUS_MULTIPLIER);
        }

        return grossPay.setScale(2, RoundingMode.HALF_UP);
    }

    private String determinePayLevel(BigDecimal grossPay) {
        if (grossPay.compareTo(LOW_MAX) < 0) {
            return "Low";
        } else if (grossPay.compareTo(STANDARD_MAX) < 0) {
            return "Standard";
        } else if (grossPay.compareTo(HIGH_MAX) < 0) {
            return "High";
        } else {
            return "Executive";
        }
    }

    private String determineEmploymentStatus(BigDecimal hoursWorked) {
        return hoursWorked.compareTo(FULL_TIME_THRESHOLD) >= 0 ? "Full-Time" : "Part-Time";
    }
}