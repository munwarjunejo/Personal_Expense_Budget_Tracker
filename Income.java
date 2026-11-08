import java.time.LocalDate;

public class Income extends Transaction {
    public Income(int id, String title, String category, double amount, LocalDate date) {
        super(id, title, category, amount, date);
    }

    @Override
    public String getType() { return "INCOME"; }
}
