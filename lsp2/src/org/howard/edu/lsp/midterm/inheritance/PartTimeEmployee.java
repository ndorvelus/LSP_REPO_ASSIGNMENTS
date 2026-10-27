package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee. Paid straight hourly pay for all hours worked,
 * with no overtime.
 *
 * @author Nathan Dorvelus
 */
public class PartTimeEmployee extends Employee {

    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked the hours worked in the week (assumed nonnegative)
     * @param hourlyRate  the hourly pay rate (assumed nonnegative)
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay as hours worked times the hourly rate.
     *
     * @return the pay amount
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}
