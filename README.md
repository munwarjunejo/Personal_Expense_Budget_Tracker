# Personal Expense & Budget Tracker

## About the Project

The Personal Expense & Budget Tracker is a console-based application developed in Java. The main purpose of this project is to manage daily income and expenses and keep track of a monthly budget.

It allows users to record transactions, check their spending, generate monthly reports, and manage their financial records.

## Features

- Add income and expenses
- View all recorded transactions
- Search transactions by title, category, or type
- Sort transactions by amount
- Generate monthly financial reports
- View category-wise expense reports
- Set a monthly budget
- Receive budget usage alerts
- Delete transactions
- Undo the last added expense
- Save and load records using local files

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Data Structures and Algorithms (DSA)
- File Handling

## OOP Concepts

This project uses the following OOP concepts:

- **Encapsulation:** Private fields and public getter methods.
- **Inheritance:** Income and Expense classes inherit from Transaction.
- **Abstraction:** Transaction is an abstract class.
- **Polymorphism:** The `getType()` method is overridden in child classes.

## Data Structures & Algorithms

- **ArrayList:** Stores transaction records.
- **Stack:** Supports undo functionality.
- **HashMap:** Calculates category-wise expenses.
- **Linear Search:** Finds matching transactions.
- **Merge Sort:** Sorts transactions by amount.

## Project Structure

The repository also includes `.gitignore` to keep generated data and build artifacts out of version control.

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

## How to Run

**Requirements:** Java JDK 8 or newer.

1. Download or clone this repository.
2. Open the project folder in your terminal.
3. Compile the Java files:

   ```bash
   javac *.java
   ```

4. Run the application:

   ```bash
   java Main
   ```

## Data Storage

The application saves transaction records in `transactions.csv` and the monthly budget in `budget.txt`. These files are created locally when data is saved.

## Purpose

This project was developed as a practical learning project to understand Java programming, OOP principles, data structures, algorithms, and file handling.

## Note

This is an educational project. It is not connected to any real bank or online payment service.
