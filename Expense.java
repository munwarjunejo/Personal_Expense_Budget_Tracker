import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(int id, String title, String category, double amount, LocalDate date) {
        super(id, title, category, amount, date);
    }

    @Override
    public String getType() { return "EXPENSE"; }
}
