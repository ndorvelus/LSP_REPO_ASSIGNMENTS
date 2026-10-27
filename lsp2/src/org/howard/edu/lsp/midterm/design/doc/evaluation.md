# Question 2, Part 1 – Design Evaluation of `CourseRegistration`

`CourseRegistration` is a "god class" in procedural style: a single method, `registerStudent()`, does every step of the registration process, and all of its data is public. The main problems are below, each tied to Arthur Riel's heuristics.

## 1. All data is public (Riel H2.1: "All data should be hidden within its class")

`studentId`, `studentName`, `email`, `courseCode`, and `creditHours` are all `public`. Any class can set `creditHours` to a negative number or set `email` to `null`, and `CourseRegistration` cannot validate or protect these values. Because other code can depend on the field names directly, changing how the data is stored (for example, storing a `Student` object instead of three strings) would break every client. This hurts both correctness and maintainability.

## 2. Low cohesion: one class with many unrelated responsibilities (Riel H2.8: "A class should capture one and only one key abstraction"; H3.2: "Do not create god classes/objects in your system")

`registerStudent()` does six separate jobs:

1. calculating tuition,
2. applying a discount policy,
3. printing a confirmation to the console,
4. saving the record to a file,
5. sending an email, and
6. logging.

These are different abstractions (billing, persistence, notification, logging) mixed into one class. Any change to any of them, such as a new tuition rate, a database instead of a file, or text messages instead of email, means editing this one method. This violates the Single Responsibility Principle discussed in class and makes the class fragile, since a change to one concern risks breaking the others.

## 3. Missing key abstractions: student and course data are mixed together (Riel H2.8; H3.4: "Beware of classes that have too much noncommunicating behavior, i.e., methods that operate on a proper subset of the data members")

A student (`studentId`, `studentName`, `email`) and a course (`courseCode`, `creditHours`) are separate real-world entities, but here they are flattened into one object. A registration should *relate* a student to a course, not *be* both. As a result, the same student's data must be copied into every registration, so their email could differ between registrations, and the model cannot express a student registering for several courses.

## 4. Behavior is separated from the data it uses / no encapsulation of policy (Riel H2.9: "Keep related data and behavior in one place")

The tuition rate (`750.0`), the full-time threshold (`12`), and the discount (`0.90`) are hard-coded "magic numbers" buried in the middle of a method that also does I/O. Tuition policy is a business rule that should live with the data it depends on (credit hours) in a class responsible for billing. As written, the policy cannot be reused (for example, to show a tuition estimate before registering) or tested without also writing a file and printing output.

There is also a correctness concern in the policy itself: the discount is applied based on `creditHours` of a *single course*, which is unlikely to reach 12. A heavy-course-load discount should depend on the student's *total* credit hours across all registrations, which this class has no way to know because it lacks a `Student` abstraction.

## 5. Tight coupling to concrete infrastructure (Riel H4.1: "Minimize the number of classes with which another class collaborates"; H2.7: "Classes should only exhibit nil or export coupling with other classes")

The class directly creates a `FileWriter` with a hard-coded file name, writes to `System.out`, and calls `new Date()`. It depends on concrete details of the file system, the console, and the system clock. Supporting a database, a different file location, or another notification channel requires changing this class instead of plugging in a different collaborator. It also cannot be unit-tested without side effects.

## 6. Poor error handling and resource management (correctness)

- `catch (Exception e) { e.printStackTrace(); }` swallows every failure. If saving fails, the method still "sends the confirmation email" and logs a successful registration, so the student is told they are registered when no record exists.
- If `writer.write(...)` throws, `writer.close()` is never called, which leaks the file handle. A try-with-resources block should be used.
- Tuition is a `double`, which can produce rounding errors for money; `BigDecimal` is more appropriate.
- The record is written as an unescaped CSV line, so a course code containing a comma would corrupt the file.

## 7. The "email" is not really sent

The email step only prints a message. That shows the design has no real notification abstraction: the console output stands in for a feature that should be its own collaborator.

## Summary

The class violates encapsulation (H2.1), captures several abstractions instead of one (H2.8, H3.2), separates policy from the data it relies on (H2.9), and is tightly coupled to concrete I/O. This leads to poor maintainability (every change touches one method), poor extensibility (no seams for new storage or notification types), poor testability, and actual correctness risks (invalid data, false confirmations, leaked file handles). The CRC-based redesign in `design.md` splits these responsibilities into focused, collaborating classes.
