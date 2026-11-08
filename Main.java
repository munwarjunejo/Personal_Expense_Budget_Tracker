import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        BudgetTracker tracker = new BudgetTracker();
        tracker.loadFromFile();

        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("     PERSONAL EXPENSE & BUDGET TRACKER");
            System.out.println("======================================");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View All Transactions");
            System.out.println("4. Search Transaction");
            System.out.println("5. Sort Transactions by Amount");
            System.out.println("6. Monthly Report");
            System.out.println("7. Category-wise Expense Report");
            System.out.println("8. Set Monthly Budget");
            System.out.println("9. Delete Transaction");
            System.out.println("10. Undo Last Added Expense");
            System.out.println("11. Save Data");
            System.out.println("0. Save and Exit");
            System.out.println("--------------------------------------");

            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1:
                    addTransaction(tracker, true);
                    break;
                case 2:
                    addTransaction(tracker, false);
                    break;
                case 3:
                    tracker.viewAll();
                    break;
                case 4:
                    System.out.print("Enter title, category or type: ");
                    tracker.search(sc.nextLine().trim());
                    break;
                case 5:
                    tracker.sortByAmount();
                    break;
                case 6:
                    tracker.monthlyReport(readMonth());
                    break;
                case 7:
                    tracker.categoryReport(readMonth());
                    break;
                case 8:
                    double budget = readDouble("Enter monthly budget (Rs.): ");
                    if (budget <= 0) System.out.println("Budget must be greater than zero.");
                    else tracker.setMonthlyBudget(budget);
                    break;
                case 9:
                    tracker.deleteTransaction(readInt("Enter transaction ID: "));
                    break;
                case 10:
                    tracker.undoLastExpense();
                    break;
                case 11:
                    tracker.saveToFile();
                    break;
                case 0:
                    tracker.saveToFile();
                    System.out.println("Thank you for using the Budget Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        sc.close();
    }

    private static void addTransaction(BudgetTracker tracker, boolean income) {
        System.out.print("Enter title/description: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter category (e.g. Food, Travel, Salary): ");
        String category = sc.nextLine().trim();
        double amount = readDouble("Enter amount (Rs.): ");
        if (title.isEmpty() || category.isEmpty() || amount <= 0) {
            System.out.println("Title/category cannot be empty and amount must be positive.");
            return;
        }
        LocalDate date = readDate();
        tracker.addTransaction(income, title, category, amount, date);
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Enter date (YYYY-MM-DD), or press Enter for today: ");
            String value = sc.nextLine().trim();
            if (value.isEmpty()) return LocalDate.now();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Example: 2026-11-08");
            }
        }
    }

    private static YearMonth readMonth() {
        while (true) {
            System.out.print("Enter month (YYYY-MM), or press Enter for current month: ");
            String value = sc.nextLine().trim();
            if (value.isEmpty()) return YearMonth.now();
            try {
                return YearMonth.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid month. Example: 2026-11");
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
