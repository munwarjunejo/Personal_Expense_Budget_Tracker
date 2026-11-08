# Personal Expense & Budget Tracker

## About

Personal Expense & Budget Tracker is a Java console application for keeping track of income, expenses, and a monthly budget. It was made as a practice project to apply object-oriented programming, data structures, algorithms, and file handling in one program.

## Features

- Add income and expenses
- View all saved transactions
- Search by title, category, or transaction type
- Display transactions sorted by amount
- Create monthly income and expense reports
- View expenses by category
- Set a monthly budget
- Get an alert after using 80% of the current month's budget or going over it
- Delete a transaction by ID
- Undo the most recently added expense during the current session
- Save and load data from local files

## Concepts Used

- **Encapsulation:** Transaction fields are private and accessed through getter methods.
- **Inheritance:** `Income` and `Expense` extend the `Transaction` class.
- **Abstraction:** `Transaction` is an abstract class.
- **Polymorphism:** `getType()` is implemented by the child classes.

## Data Structures and Algorithms

- `ArrayList` for storing transactions
- `Stack` for the undo feature
- `HashMap` for category-wise expense totals
- Linear search for finding transactions
- Merge sort for displaying transactions by amount

## Project Structure

```text
Personal_Expense_Budget_Tracker/
├── .gitignore
├── Main.java
├── Transaction.java
├── Income.java
├── Expense.java
├── BudgetTracker.java
└── README.md
```

## Requirements

- Java JDK 8 or later

## How to Run

1. Clone or download the repository.
2. Open the project folder in a terminal.
3. Compile the Java files:

   ```bash
   javac *.java
   ```

4. Start the program:

   ```bash
   java Main
   ```

## Data Storage

When the data is saved, the program creates two local files:

- `transactions.csv` for income and expense records
- `budget.txt` for the monthly budget

These files are created in the same folder where the program is run. They are not included in the repository.

## Notes

The sorting option only displays a sorted copy of the transactions; it does not change their original order. The undo stack is kept in memory, so the undo option applies to expenses added during the current run of the program. After restarting the application, previously saved expenses cannot be undone.

## Purpose

This is a learning project built while practising Java, OOP, data structures, algorithms, and file handling.
