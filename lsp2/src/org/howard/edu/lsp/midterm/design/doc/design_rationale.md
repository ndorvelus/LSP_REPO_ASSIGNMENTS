# Question 2, Part 3 – Evolving Requirements and Design Rationale

## New requirements

1. Registration records can be stored in either a **local file** or a **database**, chosen by the deployment environment.
2. Confirmations can be sent by either **email** or **text message**.

## How the Part 2 design accommodates them

The Part 2 design already isolates both concerns behind their own classes, `RegistrationRepository` and `ConfirmationSender`. `RegistrationService` only asks them to "save this registration" and "send a confirmation". It never knows *how* that happens. So the new options can be added as new kinds of these collaborators without changing `RegistrationService`, `Registration`, `Student`, `Course`, or `TuitionCalculator`.

## Changes to the CRC cards

I would make `RegistrationRepository` and `ConfirmationSender` general abstractions (interfaces or abstract classes) and add one concrete card for each option.

---

**Class:** RegistrationRepository *(abstraction)*
**Responsibilities:**
- Define the operation to save a registration record
- Report a failure if a record cannot be saved

**Collaborators:** Registration

---

**Class:** FileRegistrationRepository
**Responsibilities:**
- Save registration records to a local file
- Know the file location and record format

**Collaborators:** Registration

---

**Class:** DatabaseRegistrationRepository
**Responsibilities:**
- Save registration records to a database
- Know the database connection details and table structure

**Collaborators:** Registration

---

**Class:** ConfirmationSender *(abstraction)*
**Responsibilities:**
- Define the operation to send a registration confirmation

**Collaborators:** Registration, Student

---

**Class:** EmailConfirmationSender
**Responsibilities:**
- Send the confirmation to the student's email address

**Collaborators:** Registration, Student

---

**Class:** SmsConfirmationSender
**Responsibilities:**
- Send the confirmation as a text message to the student's phone number

**Collaborators:** Registration, Student

---

**Class:** RegistrationSystemConfig *(new)*
**Responsibilities:**
- Read the deployment environment settings
- Create the correct `RegistrationRepository` (file or database) and the correct `ConfirmationSender` (email or text)
- Supply these collaborators to `RegistrationService` when it is created

**Collaborators:** RegistrationService, FileRegistrationRepository, DatabaseRegistrationRepository, EmailConfirmationSender, SmsConfirmationSender

---

**Student** gains one responsibility: store and provide the student's phone number and preferred confirmation method, because text messages need a phone number.

**RegistrationService** keeps the same responsibilities. Its collaborators are still the abstract `RegistrationRepository` and `ConfirmationSender`, which it receives at creation time instead of creating itself.

## Key decision: who chooses the storage and confirmation type?

**Decision:** `RegistrationSystemConfig` decides which storage and confirmation implementations to use, *not* `RegistrationService`.

**Justification:** If `RegistrationService` chose the implementation itself (for example, `if (env.equals("prod")) new DatabaseRegistrationRepository() else new FileRegistrationRepository()`), it would go back to having two jobs, coordinating registration *and* knowing about deployment environments. It would also need editing every time a new storage or messaging option appeared. Following Riel's heuristic that a class should capture one key abstraction (H2.8), and keeping coupling low, the choice belongs to the one class whose job is to know the environment. `RegistrationService` then depends only on the abstractions, so it works the same in every environment and can be tested with simple in-memory fakes.

A related choice: each concrete `ConfirmationSender` owns the details of its delivery channel. `EmailConfirmationSender` knows how to format and send email, and `SmsConfirmationSender` knows how to fit a message into a text. That keeps channel-specific knowledge in one place, the class that does the sending, instead of spreading it into `RegistrationService` or `Registration`.
