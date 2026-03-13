package Loans;


public abstract class AbstractLoan implements Loan {

    private String name;
    private int term;
    private double price;

    protected AbstractLoan(String name, int term, double price) {
        this.name = name;
        this.term = term;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getTerm() {
        return term;
    }

    public double getPrice() {
        return price;
    }
}