import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class BudgetTracker {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private HashMap<String, Double> categoryTotals = new HashMap<>();
    private Stack<Integer> undoStack = new Stack<>();
    private int nextId = 1;
    private double monthlyBudget = 0.0;
    private static final String FILE_NAME = "transactions.csv";

    public void addTransaction(boolean isIncome, String title, String category,
                               double amount, LocalDate date) {
        Transaction t = isIncome
                ? new Income(nextId++, title, category, amount, date)
                : new Expense(nextId++, title, category, amount, date);
        transactions.add(t);
        if (!isIncome) {
            undoStack.push(t.getId());
        }
        System.out.println("Transaction added. Your ID is: " + t.getId());
        checkBudget(date);
    }

    public void viewAll() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        printHeader();
        for (Transaction t : transactions) t.display();
    }

    public void search(String keyword) {
        boolean found = false;
        String key = keyword.toLowerCase();
        for (Transaction t : transactions) {
            if (t.getTitle().toLowerCase().contains(key)
                    || t.getCategory().toLowerCase().contains(key)
                    || t.getType().toLowerCase().contains(key)) {
                if (!found) printHeader();
                t.display();
                found = true;
            }
        }
        if (!found) System.out.println("No matching transaction found.");
    }

    public void sortByAmount() {
        if (transactions.size() < 2) {
            System.out.println("Not enough transactions to sort.");
            return;
        }
        ArrayList<Transaction> sorted = mergeSort(new ArrayList<>(transactions));
        printHeader();
        for (Transaction t : sorted) t.display();
    }

    // DSA: Merge Sort - divide and conquer sorting algorithm.
    private ArrayList<Transaction> mergeSort(ArrayList<Transaction> list) {
        if (list.size() <= 1) return list;
        int mid = list.size() / 2;
        ArrayList<Transaction> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<Transaction> right = new ArrayList<>(list.subList(mid, list.size()));
        return merge(mergeSort(left), mergeSort(right));
    }

    private ArrayList<Transaction> merge(ArrayList<Transaction> left,
                                         ArrayList<Transaction> right) {
        ArrayList<Transaction> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getAmount() <= right.get(j).getAmount()) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));
        return result;
    }

    public void monthlyReport(YearMonth month) {
        double income = 0, expense = 0;
        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(month)) {
                if (t instanceof Income) income += t.getAmount();
                else expense += t.getAmount();
            }
        }
        System.out.println("\n===== REPORT: " + month + " =====");
        System.out.printf("Total Income : Rs. %.2f%n", income);
        System.out.printf("Total Expense: Rs. %.2f%n", expense);
        System.out.printf("Net Savings  : Rs. %.2f%n", income - expense);
        if (monthlyBudget > 0 && month.equals(YearMonth.now())) {
            System.out.printf("Monthly Budget: Rs. %.2f%n", monthlyBudget);
            System.out.printf("Budget Remaining: Rs. %.2f%n", monthlyBudget - expense);
        }
    }

    public void categoryReport(YearMonth month) {
        categoryTotals.clear();
        for (Transaction t : transactions) {
            if (t instanceof Expense && YearMonth.from(t.getDate()).equals(month)) {
                String category = t.getCategory();
                categoryTotals.put(category,
                        categoryTotals.getOrDefault(category, 0.0) + t.getAmount());
            }
        }
        if (categoryTotals.isEmpty()) {
            System.out.println("No expenses found for " + month);
            return;
        }
        System.out.println("\nExpense by category for " + month + ":");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%-20s Rs. %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    public void setMonthlyBudget(double amount) {
        monthlyBudget = amount;
        System.out.printf("Monthly budget set to Rs. %.2f%n", monthlyBudget);
        checkBudget(LocalDate.now());
    }

    private void checkBudget(LocalDate date) {
        if (monthlyBudget <= 0 || !YearMonth.from(date).equals(YearMonth.now())) return;
        double spent = 0;
        for (Transaction t : transactions) {
            if (t instanceof Expense && YearMonth.from(t.getDate()).equals(YearMonth.now())) {
                spent += t.getAmount();
            }
        }
        if (spent > monthlyBudget) {
            System.out.println("WARNING: You have exceeded your monthly budget!");
        } else if (spent >= monthlyBudget * 0.8) {
            System.out.println("Budget alert: You have used at least 80% of your budget.");
        }
    }

    public void deleteTransaction(int id) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
                undoStack.remove(Integer.valueOf(id));
                System.out.println("Transaction deleted.");
                return;
            }
        }
        System.out.println("Transaction ID not found.");
    }

    // DSA: Stack is used to undo the most recently added expense.
    public void undoLastExpense() {
        if (undoStack.isEmpty()) {
            System.out.println("There is no expense to undo.");
            return;
        }
        int id = undoStack.pop();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
                System.out.println("Last added expense (ID " + id + ") has been undone.");
                return;
            }
        }
        System.out.println("That expense no longer exists.");
    }

    public void saveToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Transaction t : transactions) {
                String title = encode(t.getTitle());
                String category = encode(t.getCategory());
                out.println(t.getId() + "," + t.getType() + "," + title + "," + category
                        + "," + t.getAmount() + "," + t.getDate());
            }
            try (PrintWriter budgetOut = new PrintWriter(new FileWriter("budget.txt"))) {
                budgetOut.println(monthlyBudget);
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous data found. Starting a new record.");
            return;
        }
        transactions.clear();
        undoStack.clear();
        int largestId = 0;
        int skippedRecords = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                String[] p = line.split(",", -1);
                try {
                    if (p.length != 6) {
                        throw new IllegalArgumentException("Expected 6 fields");
                    }
                    int id = Integer.parseInt(p[0]);
                    String type = p[1];
                    String title = decode(p[2]);
                    String category = decode(p[3]);
                    double amount = Double.parseDouble(p[4]);
                    LocalDate date = LocalDate.parse(p[5]);

                    if (id <= 0 || (!type.equals("INCOME") && !type.equals("EXPENSE"))
                            || !Double.isFinite(amount) || amount <= 0
                            || title.trim().isEmpty() || category.trim().isEmpty()) {
                        throw new IllegalArgumentException("Invalid transaction values");
                    }

                    Transaction t = type.equals("INCOME")
                            ? new Income(id, title, category, amount, date)
                            : new Expense(id, title, category, amount, date);
                    transactions.add(t);
                    largestId = Math.max(largestId, id);
                } catch (RuntimeException e) {
                    skippedRecords++;
                    System.out.println("Skipping invalid transaction record at line "
                            + lineNumber + ".");
                }
            }
            nextId = largestId + 1;
            File budgetFile = new File("budget.txt");
            if (budgetFile.exists()) {
                try (Scanner reader = new Scanner(budgetFile)) {
                    if (reader.hasNextDouble()) monthlyBudget = reader.nextDouble();
                }
            }
            System.out.println("Previous data loaded successfully.");
            if (skippedRecords > 0) {
                System.out.println("Skipped " + skippedRecords
                        + " invalid transaction record(s). Valid records were kept.");
            }
        } catch (IOException e) {
            System.out.println("Some saved data could not be loaded: " + e.getMessage());
        }
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), java.nio.charset.StandardCharsets.UTF_8);
    }

    private void printHeader() {
        System.out.printf("%-5s %-10s %-22s %-16s %-14s %s%n",
                "ID", "TYPE", "TITLE", "CATEGORY", "AMOUNT", "DATE");
        System.out.println("--------------------------------------------------------------------------------");
    }
}
