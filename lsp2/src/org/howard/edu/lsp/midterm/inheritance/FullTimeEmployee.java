package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Paid the hourly rate for the first 40 hours of the
 * week and 1.5 times the hourly rate for every hour beyond 40.
 *
 * @author Nathan Dorvelus
 */
public class FullTimeEmployee extends Employee {

    private static final double REGULAR_HOURS = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee.
     *
     * @param hoursWorked the hours worked in the week (assumed nonnegative)
     * @param hourlyRate  the hourly pay rate (assumed nonnegative)
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay with overtime at 1.5 times the hourly rate for
     * hours beyond 40.
     *
     * @return the pay amount
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();
        if (hours <= REGULAR_HOURS) {
            return hours * rate;
        }
        double overtimeHours = hours - REGULAR_HOURS;
        return REGULAR_HOURS * rate + overtimeHours * rate * OVERTIME_MULTIPLIER;
    }
}
