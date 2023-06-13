class AccountingEntry {
    private double amount;
    private String date;
    private String otherAccount;

    public AccountingEntry(double amount, String date, String otherAccount) {
        this.amount = amount;
        this.date = date;
        this.otherAccount = otherAccount;
    }

    public double getAmount() {

        return amount;
    }

    public String getDate() {

        return date;
    }

    public String getOtherAccount() {

        return otherAccount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }
}