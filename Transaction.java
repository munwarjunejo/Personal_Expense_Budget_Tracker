import java.time.LocalDate;

public abstract class Transaction {
    private int id;
    private String title;
    private String category;
    private double amount;
    private LocalDate date;

    public Transaction(int id, String title, String category, double amount, LocalDate date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public abstract String getType();

    public void display() {
        System.out.printf("%-5d %-10s %-22s %-16s Rs. %-10.2f %s%n",
                id, getType(), title, category, amount, date);
    }
}
