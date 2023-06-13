import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Account {
    private String name;
    private Set<AccountingEntry> entries;

    public Account(String name) {
        this.name = name;
        this.entries = new HashSet<>();
    }

    public AccountingEntry search(double amount) {
        for (AccountingEntry entry : entries) {
            if (entry.getAmount() == amount) {
                return entry;
            }
        }
        return null;
    }

    public void addEntry(double amount, String date, String otherAccount) {
        AccountingEntry entry = new AccountingEntry(amount, date, otherAccount);
        entries.add(entry);
    }

    public Set<AccountingEntry> getEntries() {

        return entries;
    }

    public String getName() {
        return name;
    }

    public double getTotalAmount() {
        double total = 0.0;
        for (AccountingEntry entry : entries) {
            total += entry.getAmount();
        }
        return total;
    }

    public List<Double> getAllAmounts(){
        List<Double> amounts = new ArrayList<>();
        for (AccountingEntry entry: entries){
            amounts.add(entry.getAmount());
        }
        return amounts;
    }
}
