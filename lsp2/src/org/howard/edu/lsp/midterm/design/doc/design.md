# Question 2, Part 2 – Improved Design (CRC Cards)

The redesign splits the original `CourseRegistration` god class into focused classes. Each one captures a single abstraction and hides its data. A `RegistrationService` coordinates the workflow and depends on small, replaceable collaborators for storage, notification, and logging.

---

**Class:** Student
**Responsibilities:**
- Store and protect the student's ID, name, and contact information (email, phone)
- Validate its own data (for example, non-empty ID, well-formed email)
- Provide student details to other classes

**Collaborators:** None

---

**Class:** Course
**Responsibilities:**
- Store and protect the course code and credit hours
- Validate its own data (for example, credit hours greater than 0)
- Provide course details to other classes

**Collaborators:** None

---

**Class:** Registration
**Responsibilities:**
- Represent one student enrolled in one course
- Record the registration timestamp
- Hold the tuition amount calculated for this registration
- Provide registration details (student, course, tuition, time)

**Collaborators:** Student, Course

---

**Class:** TuitionCalculator
**Responsibilities:**
- Know the tuition rate per credit hour
- Know the heavy-load discount policy (threshold and percentage)
- Calculate tuition for a registration based on credit hours

**Collaborators:** Course

---

**Class:** RegistrationRepository
**Responsibilities:**
- Save a registration record
- Hide how and where records are stored from the rest of the system
- Report a failure if a record cannot be saved

**Collaborators:** Registration

---

**Class:** ConfirmationSender
**Responsibilities:**
- Send a registration confirmation to the student
- Hide how the confirmation is delivered from the rest of the system

**Collaborators:** Registration, Student

---

**Class:** RegistrationLogger
**Responsibilities:**
- Record that a registration was processed, with its timestamp
- Record registration failures

**Collaborators:** Registration

---

**Class:** RegistrationService
**Responsibilities:**
- Coordinate the registration workflow: create the Registration, get tuition from the TuitionCalculator, save it through the RegistrationRepository, send a confirmation through the ConfirmationSender, and log the result
- Send a confirmation only after the record is saved successfully
- Report errors to the caller instead of hiding them

**Collaborators:** Student, Course, Registration, TuitionCalculator, RegistrationRepository, ConfirmationSender, RegistrationLogger

---

## How this fixes the original problems

| Original problem | Fix in the redesign |
|---|---|
| Public fields (H2.1) | Every class keeps its data private and validates it |
| God class / low cohesion (H2.8, H3.2) | Each responsibility (billing, storage, notification, logging) has its own class |
| Student and course data mixed together | `Student`, `Course`, and `Registration` are separate abstractions |
| Hard-coded tuition policy | `TuitionCalculator` owns the rate, threshold, and discount |
| Tight coupling to `FileWriter`, `System.out` | `RegistrationService` talks to `RegistrationRepository` and `ConfirmationSender`, not to concrete I/O |
| Swallowed exceptions, false confirmations | `RegistrationService` sends a confirmation only after a successful save and reports failures |
