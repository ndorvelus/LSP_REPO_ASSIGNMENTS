package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Employee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final BigDecimal hoursWorked;
    private final BigDecimal hourlyRate;
    private final BigDecimal grossPay;
    private final String payLevel;
    private final String employmentStatus;

    public Employee(int employeeId, String name, String department,
                     BigDecimal hoursWorked, BigDecimal hourlyRate,
                     BigDecimal grossPay, String payLevel, String employmentStatus) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.grossPay = grossPay;
        this.payLevel = payLevel;
        this.employmentStatus = employmentStatus;
    }

    public int getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public BigDecimal getHoursWorked() { return hoursWorked; }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public BigDecimal getGrossPay() { return grossPay; }
    public String getPayLevel() { return payLevel; }
    public String getEmploymentStatus() { return employmentStatus; }

    public String toCsvRow() {
        return employeeId + "," +
                name + "," +
                department + "," +
                hoursWorked.setScale(2, RoundingMode.HALF_UP) + "," +
                hourlyRate.setScale(2, RoundingMode.HALF_UP) + "," +
                grossPay.setScale(2, RoundingMode.HALF_UP) + "," +
                payLevel + "," +
                employmentStatus;
    }
}