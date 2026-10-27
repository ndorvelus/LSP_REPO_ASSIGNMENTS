# Development log for Question 3 (refactoring with inheritance and polymorphism)

## AI tools

Claude Code (Anthropic's desktop app, Claude Opus 5.5) wrote the classes I'm submitting. My prompt in https://claude.ai/share/00f84a66-6742-40db-85ec-acba21467d4f. It copied the instructor's Driver in unchanged and replaced the `if` chain with an abstract `Employee` class that declares `calculatePay()`. `FullTimeEmployee` (1.5 times the rate past 40 hours), `PartTimeEmployee`, and `Contractor` (15% premium) each override that method, so the Driver gets the right pay through an `Employee` reference. It added Javadoc, used no design pattern, and the Driver result was 12 passed, 0 failed. 

## Other sources

The midterm handout gave the `EmployeePayCalculator` class, the pay rules, and the Driver. I didn't use any websites.
