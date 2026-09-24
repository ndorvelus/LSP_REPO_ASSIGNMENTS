# DESIGN.md — Assignment #3 Object-Oriented Refactoring

## 1. How was your Assignment #2 solution organized?

Assignment #2 was one class, `ETLPipeline`, with everything as static methods.`processRow` did normalization, validation, pay calculation, and classification all in one place. The only data type, `TransformedRecord`, was a plain bag of public fields with no behavior of its own.

## 2. What design changes did you make for Assignment #3?

I split the single class into six classes, each owning one stage of the pipeline: `Employee` and `RawEmployeeData` as data types, `EmployeeRecordParser` for validation, `PayrollCalculator` for the business rules, `EmployeeCsvReader`/`EmployeeCsvWriter` for file I/O, and `ETLPipeline` left only to wire them together and report the summary.

## 3. What classes or abstractions did you introduce, and why?

`RawEmployeeData` and `Employee` are kept separate so `PayrollCalculator` can only ever receive data that already passed validation, and `Employee` owns its own CSV formatting rather than a separate method reaching into a field bag. `EmployeeRecordParser` and `PayrollCalculator` are split because "is this row valid?" and "what does this employee earn?" are different concerns that were previously tangled together in one method.

## 4. How did you divide responsibilities differently?

Assignment #2 divided work by pipeline stage as static methods sharing one scope; Assignment #3 divides it by single responsibility as objects, so each class has exactly one reason to change and `ETLPipeline` no longer does any calculation or parsing itself.

## 5. Why do you believe your Assignment #3 design is an improvement?

Each responsibility is now isolated and independently changeable. A new overtime rule only touches `PayrollCalculator`, and a new output format only touches `Employee` while the program still produces identical console output and CSV results to Assignment #2.

## AI and Internet Resources

Claude was used to help design and write this refactoring. No other AI tools or Internet resources were used.  https://claude.ai/share/26ed8607-1b1a-4e04-b84a-bbd9a919525d