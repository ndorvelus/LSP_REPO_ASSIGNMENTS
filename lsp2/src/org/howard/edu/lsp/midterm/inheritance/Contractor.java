package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor. Paid the hourly rate plus a 15% premium on all hours
 * worked, with no overtime.
 *
 * @author Nathan Dorvelus
 */
public class Contractor extends Employee {

    private static final double PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a contractor.
     *
     * @param hoursWorked the hours worked in the week (assumed nonnegative)
     * @param hourlyRate  the hourly pay rate (assumed nonnegative)
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates weekly pay as hours worked times the hourly rate, plus a
     * 15% premium.
     *
     * @return the pay amount
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * PREMIUM_MULTIPLIER;
    }
}
