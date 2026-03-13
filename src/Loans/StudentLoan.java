package Loans;

public class StudentLoan extends AbstractLoan {

    private double interest;

    public StudentLoan(String name, int term, double price) {
        super(name, term, price);
        interest = 0.05;
    }

    public String fullPriceToPay() {
        return "The full price that you have to pay is "
                + (getPrice() + (getPrice() * interest));
    }
}