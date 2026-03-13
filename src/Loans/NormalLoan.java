package Loans;

public class NormalLoan extends AbstractLoan {

    private final double interest;

    public NormalLoan(String name, int term, double price) {
        super(name, term, price);
        interest = 0.1;
    }

    public String fullPriceToPay() {
        return "The full price that you have to pay is "
                + (getPrice() + (getPrice() * interest));
    }
}