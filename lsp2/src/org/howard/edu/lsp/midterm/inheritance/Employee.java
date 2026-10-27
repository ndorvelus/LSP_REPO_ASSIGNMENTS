package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. Stores the hours worked and
 * hourly rate shared by every employee, and declares {@link #calculatePay()},
 * which each subclass overrides with its own pay rule. Callers can compute
 * pay through an {@code Employee} reference without knowing the concrete
 * type, replacing the original conditional on an employee-type string.
 *
 * @author Nathan Dorvelus
 */
public abstract class Employee {

    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an employee with the given hours worked and hourly rate.
     *
     * @param hoursWorked the hours worked in the week (assumed nonnegative)
     * @param hourlyRate  the hourly pay rate (assumed nonnegative)
     */
    public Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked in the week.
     *
     * @return the hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the hourly pay rate.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay for the week.
     *
     * @return the pay amount
     */
    public abstract double calculatePay();
}
