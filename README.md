# Personal Expense & Budget Tracker

A console-based Java program for recording income and expenses and checking a monthly budget.

## Features

- Add income and expenses
- View, search, and sort transactions
- View monthly and category-wise reports
- Set a monthly budget and get alerts
- Delete a transaction or undo the last added expense
- Save and load records from local files

## Java concepts used

The project uses classes, inheritance, encapsulation, and polymorphism. It also uses `ArrayList`, `Stack`, `HashMap`, linear search, and merge sort.

## How to run

Use Java 8 or newer. Open a terminal in the project folder and run:

```bash
javac *.java
java Main
```

## Saved data

When saved, the program writes `transactions.csv` and `budget.txt` in the same folder. The records stay on the local computer; the app is not connected to a bank or payment service.
